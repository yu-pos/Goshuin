package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import bean.GoshuinBook;
import bean.GoshuinBookStickerAttachment;
import bean.RegdGoshuinBookDesign;
import bean.RegdGoshuinBookSticker;

public class GoshuinBookDao extends Dao {

	/*
	 * 処理一覧
	 * ・指定したIDの御朱印帳の情報を取得() : GoshuinBook - GetById(id:int) : GoshuinBook
	 * ・対象の利用者の御朱印帳一覧を取得(User:user) : List<GoshuinBook> -  SearchByUser(User:user) : List<GoshuinBook>
	 * ・新たな御朱印帳を登録() : Pair - Save() : Pair - Pairには成功/失敗を表すBooleanと登録したデータのIDが格納される
	 * ・御朱印帳カスタマイズ(goshuinBook:GoshuinBook) : boolean - Save(goshuinBook:Goshuinbook) - オーバーロードで実装
	 */

	/**
	 * getByIdメソッド 御朱印帳IDを指定して御朱印帳インスタンスを1件取得する
	 *
	 * @param id:int
	 *            御朱印帳ID
	 * @return 御朱印帳クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public GoshuinBook getById(int id) throws Exception {
		// 御朱印帳インスタンスを初期化
		GoshuinBook goshuinBook = new GoshuinBook();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT id, user_id, goshuin_book_design_id, updated_at, created_at"
					+ "FROM user WHERE id = ?");
			// プリペアードステートメントに御朱印帳IDをバインド
			statement.setInt(1, id);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			// Daoを初期化
			RegdGoshuinBookDesignDao regdGoshuinBookDesignDao = new RegdGoshuinBookDesignDao();
			GoshuinBookStickerAttachmentDao goshuinBookStickerAttachmentDao = new GoshuinBookStickerAttachmentDao();
			OwnedGoshuinDao ownedGoshuinDao = new OwnedGoshuinDao();

			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// 御朱印帳インスタンスに検索結果をセット
				goshuinBook.setId(resultSet.getInt("id"));
				goshuinBook.setUserId(resultSet.getInt("user_id"));
				goshuinBook.setGoshuinBookDesign(regdGoshuinBookDesignDao.getById(resultSet.getInt("goshuin_book_design_id")));
				goshuinBook.setAttachedStickerList(goshuinBookStickerAttachmentDao.searchByGoshuinBook(goshuinBook.getId()));
				goshuinBook.setGoshuinList(ownedGoshuinDao.searchByGoshuinBook(goshuinBook.getId()));
				goshuinBook.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
				goshuinBook.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

			} else {
				// リザルトセットが存在しない場合
				// 利用者インスタンスにnullをセット
				goshuinBook = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return goshuinBook;
	}

	/**
	 * searchByUserメソッド 利用者を指定して所持御朱印帳リストを取得する
	 *
	 * @param user:User
	 *            利用者インスタンス
	 * @return 御朱印帳クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public List<GoshuinBook> searchByUser(int userId) throws Exception{

		// 御朱印帳リスト
		List<GoshuinBook> list = new ArrayList<>();

		//御朱印帳ID格納リスト
		Map<Integer, GoshuinBook> goshuinBookMap = new HashMap<>();

		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {

			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT goshuin_book.id, goshuin_book.user_id,"
													+ " goshuin_book.goshuin_book_design_id, regd_goshuin_book_design.goshuin_book_design_group_id, regd_goshuin_book_design.name, regd_goshuin_book_design.image_path,"
													+ " goshuin_book_sticker_attachment.goshuin_book_sticker_id, goshuin_book_sticker_attachment.x_pos, goshuin_book_sticker_attachment.y_pos, goshuin_book_sticker_attachment.rotation, goshuin_book_sticker_attachment.name, goshuin_book_sticker_attachment.image_path,"
													+ " goshuin_book.updated_at, goshuin_book.created_at"
													+ " FROM goshuin_book "
													+ " JOIN (SELECT goshuin_book_id, goshuin_book_sticker_id, x_pos, y_pos, rotation, name, image_path FROM goshuin_book_sticker_attachment JOIN regd_goshuin_book_sticker ON goshuin_book_sticker_attachment.goshuin_book_sticker_id = regd_goshuin_book_sticker.id) as goshuin_book_sticker_attachment  ON goshuin_book.id = goshuin_book_sticker_attachment.goshuin_book_id"
													+ " JOIN regd_goshuin_book_design ON goshuin_book.goshuin_book_design_id = regd_goshuin_book_design.id"
													+ " WHERE user_id = ?"
													+ " ORDER BY goshuin_book.id desc"
													);
			// プリペアードステートメントに御朱印帳IDをバインド
			statement.setInt(1, userId);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();


			// Daoを初期化
			OwnedGoshuinDao ownedGoshuinDao = new OwnedGoshuinDao();


			while(resultSet.next())  {
				// リザルトセットが存在する場合
				// 御朱印帳インスタンスに検索結果をセット
				GoshuinBook goshuinBook = new GoshuinBook();
				RegdGoshuinBookSticker regdGoshuinBookSticker = new RegdGoshuinBookSticker();
				GoshuinBookStickerAttachment attachedSticker = new GoshuinBookStickerAttachment();


				if (!goshuinBookMap.containsKey(resultSet.getInt("goshuin_book.id"))) {

					RegdGoshuinBookDesign regdGoshuinBookDesign = new RegdGoshuinBookDesign();

					goshuinBook.setId(resultSet.getInt("goshuin_book.id"));
					goshuinBook.setUserId(resultSet.getInt("goshuin_book.user_id"));

					// 登録デザインインスタンスに情報をセットし、インスタンスを御朱印帳にセット
					regdGoshuinBookDesign.setId(resultSet.getInt("goshuin_book.goshuin_book_design_id"));
					regdGoshuinBookDesign.setGoshuinBookDesignGroupId(resultSet.getInt("regd_goshuin_book_design.goshuin_book_design_group_id"));
					regdGoshuinBookDesign.setName(resultSet.getString("regd_goshuin_book_design.name"));
					regdGoshuinBookDesign.setImagePath(resultSet.getString("regd_goshuin_book_design.image_path"));

					goshuinBook.setGoshuinBookDesign(regdGoshuinBookDesign);


					// 貼付ステッカーインスタンスに情報をセットし、インスタンスを御朱印帳にセット
					attachedSticker.setGoshuinBookId(resultSet.getInt("goshuin_book.id"));
					attachedSticker.setxPos(resultSet.getDouble("goshuin_book_sticker_attachment.x_pos"));
					attachedSticker.setyPos(resultSet.getDouble("goshuin_book_sticker_attachment.y_pos"));
					attachedSticker.setRotation(resultSet.getDouble("goshuin_book_sticker_attachment.rotation"));

					regdGoshuinBookSticker.setId(resultSet.getInt("regd_goshuin_book_sticker.id"));
					regdGoshuinBookSticker.setName(resultSet.getString("regd_goshuin_book_sticker.name"));
					regdGoshuinBookSticker.setImagePath(resultSet.getString("regd_goshuin_book_sticker.image_path"));

					attachedSticker.setGoshuinBookSticker(regdGoshuinBookSticker);


					goshuinBook.setGoshuinList(ownedGoshuinDao.searchByGoshuinBook(goshuinBook.getId()));
					goshuinBook.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
					goshuinBook.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

				} else {

					goshuinBook = goshuinBookMap.get(resultSet.getInt("goshuin_book.id"));

					// 貼付ステッカーインスタンスに情報をセットし、インスタンスを御朱印帳にセット
					attachedSticker.setGoshuinBookId(resultSet.getInt("goshuin_book.id"));
					attachedSticker.setxPos(resultSet.getDouble("goshuin_book_sticker_attachment.x_pos"));
					attachedSticker.setyPos(resultSet.getDouble("goshuin_book_sticker_attachment.y_pos"));
					attachedSticker.setRotation(resultSet.getDouble("goshuin_book_sticker_attachment.rotation"));

					regdGoshuinBookSticker.setId(resultSet.getInt("regd_goshuin_book_sticker.id"));
					regdGoshuinBookSticker.setName(resultSet.getString("regd_goshuin_book_sticker.name"));
					regdGoshuinBookSticker.setImagePath(resultSet.getString("regd_goshuin_book_sticker.image_path"));

					goshuinBook.addAttachedStickerList(attachedSticker);

				}

				goshuinBookMap.put(goshuinBook.getId(), goshuinBook);
			}




		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return list;

	}

	/**
	 * insertメソッド 御朱印帳情報を登録
	 *
	 * @param user_id: int
	 *           利用者ID
	 *
	 * @return Pair<成功可否:Boolean, 登録された御朱印帳ID:Integer>
	 * @throws Exception
	 */
	public Pair<Boolean, Integer> insert(int userId) throws Exception {
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		// 実行件数
		int count = 0;

		//登録した御朱印帳のID
		int goshuinBookId;

		Pair<Boolean, Integer> pair;

		try {





			// プリペアードステートメントにINSERT文をセット
			statement = connection.prepareStatement("INSERT INTO goshuin_book(user_id, goshuin_book_design_id) VALUES(?, 1)");
			// プリペアードステートメントに値をバインド
			statement.setInt(1, userId);

			// プリペアードステートメントを実行
			count = statement.executeUpdate();

			statement.close();

			//登録した御朱印帳のIDを取得
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT id"
					+ "FROM goshuin_book WHERE user_id = ? ORDER BY id DESC LIMIT 1");
			statement.setInt(1, userId);

			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();
			// 検索結果をセット
			goshuinBookId = resultSet.getInt("id");

		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		if (count == 1) {
			// 実行件数1件の場合
			pair = Pair.of(Boolean.TRUE, Integer.valueOf(goshuinBookId));
		} else {
			// 実行件数がそれ以外の場合
			pair = Pair.of(Boolean.FALSE, null);
		}

		return pair;
	}

	/**
	 * updateメソッド 御朱印帳情報を登録または更新
	 *
	 * @param goshuinBook:GoshuinBook
	 *            御朱印帳情報
	 * @return 成功可否
	 * @throws Exception
	 */
	public boolean update(GoshuinBook goshuinBook) throws Exception {
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		// 実行件数
		int count = 0;

		GoshuinBookStickerAttachmentDao goshuinBookStickerAttachmentDao = new GoshuinBookStickerAttachmentDao();

		try {

			// 情報を更新
			// プリペアードステートメントにUPDATE文をセット
			statement = connection.prepareStatement("UPDATE goshuin_book SET user_id = ?, goshuin_book_design_id = ?, updated_at = CURRENT_DATETIME WHERE id = ?");
			// プリペアードステートメントに値をバインド
			statement.setInt(1, goshuinBook.getUserId());
			statement.setInt(2, goshuinBook.getGoshuinBookDesign().getId());
			statement.setInt(3, goshuinBook.getId());

			// プリペアードステートメントを実行
			count = statement.executeUpdate();

			//ステッカー情報を登録
			goshuinBookStickerAttachmentDao.update(goshuinBook);






		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		if (count == 1) {
			// 実行件数1件の場合
			return true;
		} else {
			// 実行件数がそれ以外の場合
			return false;
		}
	}
}

