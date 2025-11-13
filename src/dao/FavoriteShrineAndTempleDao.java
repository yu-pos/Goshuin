package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.ShrineAndTemple;

public class FavoriteShrineAndTempleDao {

	//利用者のお気に入り神社仏閣情報を取得:(shrineAndTempleId:int)list<FavoriteShrineAndTemple>
	//対象のお気に入り神社仏閣を削除する(favoriteShrineAndTemple:FavoriteShrineAndTemple):boolean
	//神社仏閣情報を登録(favoriteShrineAndTemple:FavoriteShrineAndTemple):boolean
	//利用者のお気に入り神社仏閣ID一覧を取得():list<int>
	/**
	 * getByIdメソッド 利用者IDを指定して利用者インスタンスを1件取得する
	 *
	 * @param id:int
	 *            利用者ID
	 * @return 利用者クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public ShrineAndTemple getById(int id) throws Exception {
		//shrineAndTempleDao.getById(取得したい神社仏閣のID)
		// 利用者インスタンスを初期化
		ShrineAndTemple shrineAndTemple = new ShrineAndTemple();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT * FROM shrine_and_temple WHERE id=?");
			// プリペアードステートメントに神社仏閣IDをバインド
			statement.setInt(1, id);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();


			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// 利用者インスタンスに検索結果をセット

				favoriteshrineAndTemple.setShrine_and_temple_id(resultSet.getString("setshrine_and_temple_id"));
				favoriteshrineAndTemple.setUser_id(resultSet.getString("setuser_id"));

				favoriteshrineAndTemple.setUpdatedAtuserId(resultSet.getTimestamp("updated_atuser_id").toLocalDateTime());
				favoriteshrineAndTemple.setCreatedt(resultSet.getTimestamp("created_at").toLocalDateTime());

			} else {
				// リザルトセットが存在しない場合
				// 利用者インスタンスにnullを


		favoriteshrineandtemple = null;
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

		return favoriteshrineandtemple;
	}

}
