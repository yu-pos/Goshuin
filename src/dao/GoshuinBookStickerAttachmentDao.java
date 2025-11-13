package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.GoshuinBook;
import bean.GoshuinBookStickerAttachment;

public class GoshuinBookStickerAttachmentDao extends Dao {

	/*
	 * 処理一覧
	 * ・指定したIDの御朱印帳のステッカー貼付情報一覧を取得() : GoshuinBook - GetById(id:int) : GoshuinBook
	 */

	public List<GoshuinBookStickerAttachment> searchByGoshuinBook(GoshuinBook goshuinBook) throws Exception {
		// 御朱印帳ステッカー貼付インスタンスリストを初期化
		List<GoshuinBookStickerAttachment> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT goshuin_book_id, goshuin_book_sticker_id, x_pos, y_pos, rotation, updated_at, created_at"
					+ "FROM user WHERE goshuin_book_id = ?");
			// プリペアードステートメントに御朱印帳IDをバインド
			statement.setInt(1, goshuinBook.getId());
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();


			// Daoを初期化
			RegdGoshuinBookStickerDao regdGoshuinBookStickerDao = new RegdGoshuinBookStickerDao();
			OwnedGoshuinDao ownedGoshuinDao = new OwnedGoshuinDao();

			while(resultSet.next())  {
				// リザルトセットが存在する場合
				// 御朱印帳インスタンスに検索結果をセット
				GoshuinBookStickerAttachment goshuinBookStickerAttachment = new GoshuinBookStickerAttachment();

				goshuinBookStickerAttachment.setGoshuinBookId(resultSet.getInt("goshuin_book_id"));
				goshuinBookStickerAttachment.setGoshuinBookSticker(regdGoshuinBookStickerDao.getById(resultSet.getInt("goshuin_book_sticker_id")));
				goshuinBookStickerAttachment.setxPos(resultSet.getDouble("x_pos"));
				goshuinBookStickerAttachment.setyPos(resultSet.getDouble("y_pos"));
				goshuinBookStickerAttachment.setRotation(resultSet.getDouble("rotation"));
				goshuinBook.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
				goshuinBook.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

				list.add(goshuinBookStickerAttachment);

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
	 * updateメソッド ステッカー貼付情報を更新
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


		try {

			// 情報を更新

			// 一度DBに保存されている対象御朱印帳のステッカー情報を削除
			// プリペアードステートメントにDELETE文をセット
			statement = connection.prepareStatement("DELETE FROM goshuin_book_sticker_attachment WHERE goshuin_book_id = ?");
			// プリペアードステートメントに値をバインド
			statement.setInt(1, goshuinBook.getId());

			// プリペアードステートメントを実行
			statement.executeUpdate();

			statement.close();

			// goshuinBookに保存されているステッカー貼付情報を登録
			for ( GoshuinBookStickerAttachment goshuinBookStickerAttachment : goshuinBook.getAttachedStickerList()) {
				// プリペアードステートメントにDELETE文をセット
				statement = connection.prepareStatement("INSERT INTO goshuin_book_sticker_attachment(goshuin_book_id, goshuin_book_sticker_id, x_pos, y_pos, rotation) values(?, ?, ?, ?, ?)");

				// プリペアードステートメントに値をバインド
				statement.setInt(1, goshuinBook.getId());
				statement.setInt(2, goshuinBookStickerAttachment.getGoshuinBookSticker().getId());
				statement.setDouble(3, goshuinBookStickerAttachment.getxPos());
				statement.setDouble(4, goshuinBookStickerAttachment.getyPos());
				statement.setDouble(5, goshuinBookStickerAttachment.getRotation());

				// プリペアードステートメントを実行
				count += statement.executeUpdate();
				statement.close();
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

		if (count == goshuinBook.getAttachedStickerList().size()) {
			// 実行件数がステッカー情報数と同数の場合
			return true;
		} else {
			// 実行件数がそれ以外の場合
			return false;
		}
	}
}

