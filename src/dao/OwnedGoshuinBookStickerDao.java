package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.OwnedGoshuinBookSticker;
import bean.User;

public class OwnedGoshuinBookStickerDao extends Dao {

	/* =========================
    単一取得
    ========================= */
	/**
	 *
	 *
	 * @param
	 *
	 * @return
	 * @throws
	 */

	public List<OwnedGoshuinBookSticker> searchByUser(User user) throws Exception {

		// リストを初期化
		List<OwnedGoshuinBookSticker> list = new ArrayList<>();
		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;



		try {
			statement = connection.prepareStatement("SELECT * FROM owned_goshuin_book_sticker WHERE user_id = ?");
			// プリペアードステートメントに御朱印帳IDをバインド
			statement.setInt(1, user.getId());
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			RegdGoshuinBookStickerDao regdGoshuinBookStickerDao = new RegdGoshuinBookStickerDao();

			while(resultSet.next()) {

				OwnedGoshuinBookSticker ownedGoshuinBookSticker = new OwnedGoshuinBookSticker();

				ownedGoshuinBookSticker.setGoshuinBookSticker(regdGoshuinBookStickerDao.getById(resultSet.getInt("goshuin_book_sticker_id")));
				ownedGoshuinBookSticker.setUserId(resultSet.getInt("user_id"));
				ownedGoshuinBookSticker.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
				ownedGoshuinBookSticker.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

				list.add(ownedGoshuinBookSticker);
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

	//所持御朱印帳ステッカー情報を登録():boolean

}
