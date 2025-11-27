package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
					+ " FROM goshuin_book WHERE id = ?");
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
	public List<GoshuinBook> searchByUser(int userId) throws Exception {

	    List<GoshuinBook> list = new ArrayList<>();
	    Map<Integer, GoshuinBook> goshuinBookMap = new HashMap<>();

	    Connection connection = getConnection();
	    PreparedStatement statement = null;

	    try {
	        statement = connection.prepareStatement(
	            "SELECT " +
	            "  gb.id AS goshuin_book_id," +
	            "  gb.user_id AS user_id," +
	            "  gb.goshuin_book_design_id AS design_id," +
	            "  d.goshuin_book_design_group_id AS design_group_id," +
	            "  d.name AS design_name," +
	            "  d.image_path AS design_image_path," +
	            "  s.goshuin_book_sticker_id AS sticker_id," +
	            "  s.x_pos AS x_pos," +
	            "  s.y_pos AS y_pos," +
	            "  s.rotation AS rotation," +
	            "  s.name AS sticker_name," +
	            "  s.image_path AS sticker_image_path," +
	            "  gb.updated_at AS updated_at," +
	            "  gb.created_at AS created_at " +
	            "FROM goshuin_book gb " +
	            // ★ ステッカーは LEFT JOIN（0枚でも本は出す）
	            "LEFT JOIN (" +
	            "  SELECT " +
	            "    a.goshuin_book_id, a.goshuin_book_sticker_id, a.x_pos, a.y_pos, a.rotation," +
	            "    r.name, r.image_path " +
	            "  FROM goshuin_book_sticker_attachment a " +
	            "  JOIN regd_goshuin_book_sticker r " +
	            "    ON a.goshuin_book_sticker_id = r.id" +
	            ") s ON gb.id = s.goshuin_book_id " +
	            "JOIN regd_goshuin_book_design d " +
	            "  ON gb.goshuin_book_design_id = d.id " +
	            "WHERE gb.user_id = ? " +
	            "ORDER BY gb.id DESC"
	        );

	        statement.setInt(1, userId);
	        ResultSet resultSet = statement.executeQuery();

	        OwnedGoshuinDao ownedGoshuinDao = new OwnedGoshuinDao();

	        while (resultSet.next()) {

	            int bookId = resultSet.getInt("goshuin_book_id");

	            GoshuinBook goshuinBook = goshuinBookMap.get(bookId);
	            if (goshuinBook == null) {
	                goshuinBook = new GoshuinBook();
	                goshuinBook.setId(bookId);
	                goshuinBook.setUserId(resultSet.getInt("user_id"));

	                RegdGoshuinBookDesign design = new RegdGoshuinBookDesign();
	                design.setId(resultSet.getInt("design_id"));
	                design.setGoshuinBookDesignGroupId(resultSet.getInt("design_group_id"));
	                design.setName(resultSet.getString("design_name"));
	                design.setImagePath(resultSet.getString("design_image_path"));
	                goshuinBook.setGoshuinBookDesign(design);

	                // この御朱印帳の御朱印リスト
	                goshuinBook.setGoshuinList(ownedGoshuinDao.searchByGoshuinBook(bookId));

	                goshuinBook.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
	                goshuinBook.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

	                goshuinBookMap.put(bookId, goshuinBook);
	            }

	            // ★ sticker_id が NULL の行（ステッカーなし）の場合はスキップ
	            Integer stickerIdObj = (Integer) resultSet.getObject("sticker_id");
	            if (stickerIdObj != null) {
	                GoshuinBookStickerAttachment attachedSticker = new GoshuinBookStickerAttachment();
	                attachedSticker.setGoshuinBookId(bookId);

	                RegdGoshuinBookSticker regdSticker = new RegdGoshuinBookSticker();
	                regdSticker.setId(stickerIdObj);
	                regdSticker.setName(resultSet.getString("sticker_name"));
	                regdSticker.setImagePath(resultSet.getString("sticker_image_path"));
	                attachedSticker.setGoshuinBookSticker(regdSticker);

	                attachedSticker.setxPos(resultSet.getDouble("x_pos"));
	                attachedSticker.setyPos(resultSet.getDouble("y_pos"));
	                attachedSticker.setRotation(resultSet.getDouble("rotation"));

	                goshuinBook.addAttachedStickerList(attachedSticker);
	            }
	        }

	        // map → list に変換
	        list = new ArrayList<>(goshuinBookMap.values());

	        System.out.println("[DEBUG] searchByUser: 本の件数 = " + list.size());

	    } catch (Exception e) {
	        throw e;
	    } finally {
	        if (statement != null) {
	            try { statement.close(); } catch (SQLException sqle) { throw sqle; }
	        }
	        if (connection != null) {
	            try { connection.close(); } catch (SQLException sqle) { throw sqle; }
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

	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    ResultSet keys = null;

	    int count = 0;
	    Integer newId = null;

	    try {
	        // ★ 御朱印帳を1冊発行（デザインIDは仮に1固定）
	        statement = connection.prepareStatement(
	            "INSERT INTO goshuin_book(user_id, goshuin_book_design_id) VALUES(?, 1)",
	            Statement.RETURN_GENERATED_KEYS
	        );
	        statement.setInt(1, userId);

	        count = statement.executeUpdate();

	        if (count == 1) {
	            // ★ ここが今回のポイント
	            keys = statement.getGeneratedKeys();
	            if (keys.next()) {
	                newId = keys.getInt(1); // ← rs.next() してから getInt する！
	            } else {
	                // ID が返ってこなかった場合
	                return Pair.of(false, null);
	            }
	        } else {
	            // 挿入失敗
	            return Pair.of(false, null);
	        }

	    } catch (Exception e) {
	        throw e;
	    } finally {
	        if (keys != null) {
	            try { keys.close(); } catch (SQLException e) { throw e; }
	        }
	        if (statement != null) {
	            try { statement.close(); } catch (SQLException e) { throw e; }
	        }
	        if (connection != null) {
	            try { connection.close(); } catch (SQLException e) { throw e; }
	        }
	    }

	    // 成功フラグ true と 発行されたIDを返す
	    return Pair.of(true, newId);
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
			statement = connection.prepareStatement("UPDATE goshuin_book SET user_id = ?, goshuin_book_design_id = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?");
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

