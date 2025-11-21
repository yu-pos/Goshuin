package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Rank;

public class RankDao extends Dao{


	//

	/**
	 * getByIdメソッド ランクIDを指定してランクインスタンスを1件取得する
	 *
	 * @param id:int
	 *            ランクID
	 * @return ランククラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public Rank getById(int id) throws Exception {
		//shrineAndTempleDao.getById(取得したい神社仏閣のID)
		// イベントインスタンスを初期化
		Rank rank = new Rank();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT * FROM rank WHERE id=?");
			// プリペアードステートメントに神社仏閣IDをバインド
			statement.setInt(1, id);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();


			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// 利用者インスタンスに検索結果をセット

				rank.setId(resultSet.getInt("id"));
				rank.setName(resultSet.getString("name"));
				rank.setRankUpQuantity(resultSet.getInt("rank_up_quantity"));
				rank.setImagePath(resultSet.getString("image_path"));
	            rank.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
				rank.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

			} else {
				// リザルトセットが存在しない場合
				// 利用者インスタンスにnullを


				rank = null;
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

		return rank;
	}



}
