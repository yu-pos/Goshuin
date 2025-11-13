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
				goshuinBookStickerAttachment.setGoshuinBookSticker(regdGoshuinBookStickerDao.getById("goshuin_book_sticker_id"));
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

}

