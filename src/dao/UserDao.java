package dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.User;

public class UserDao extends Dao {

	/*
	 * 処理一覧
	 * ・利用者情報を更新(User:user) : boolean - Save(User:user) - 新規登録・使用中御朱印帳変更・御朱印カウント・ランクの更新をこれで行う
	 * ・IDで利用者情報を取得(id:int) : User - SearchById(id:int)
	 * ・ポイント付与(User:user, point:int) : boolean - AddPoint(User:user, point:int)
	 * ・ポイント消費(User:user, point:int) : boolean - SubPoint(User:user, point:int)
	 * ・ログインする(telNumber:String, password:String) : boolean - login(telNumber:String, password:String)
	 */

	/**
	 * Getメソッド 利用者IDを指定して利用者インスタンスを1件取得する
	 *
	 * @param id:int
	 *            利用者ID
	 * @return 利用者クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public User Get(int id) throws Exception {
		// 教員インスタンスを初期化
		User user = new User();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT id, user_name, real_name, birth_date, address, tel_number, password, active_goshuin_book_id, point, rank, goshuin_count, profile_image_path, my_goshuin_book_id, is_my_goshuin_book_public, last_login_at, updated_at, created_at"
					+ "FROM user WHERE id = ?");
			// プリペアードステートメントに教員IDをバインド
			statement.setInt(1, id);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			// 御朱印帳Daoを初期化
			GoshuinBookDao goshuinBookDao = new GoshuinBookDao();

			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// 教員インスタンスに検索結果をセット
				user.setId(resultSet.getInt("id"));
				user.setUserName(resultSet.getString("user_name"));
				user.setRealName(resultSet.getString("real_name"));
				user.setBirthDate(resultSet.getTimestamp("real_name").toLocalDateTime());
				user.setAddress(resultSet.getString("address"));
				user.setTelNumber(resultSet.getString("tel_number"));
				user.setPassword(resultSet.getString("password"));
				user.setActiveGoshuinBook(goshuinBookDao.SearchById(resultSet.getInt("active_goshuin_book_id")));
				user.setPoint(resultSet.getInt("point"));
				user.setRank(resultSet.getInt("rank"));
				user.setGoshuinCount(resultSet.getInt("goshuin_count"));
				user.setProfileImagePath(resultSet.getString("profile_image_path"));
				user.setMyGoshuinBook(goshuinBookDao.SearchById(resultSet.getInt("my_goshuin_book_id")));
				user.setMyGoshuinBookPublic(resultSet.getBoolean("is_my_goshuin_book_public"));
				user.setLastLoginAt(resultSet.getTimestamp("last_login_at").toLocalDateTime());
				user.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
				user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
			} else {
				// リザルトセットが存在しない場合
				// 教員インスタンスにnullをセット
				user = null;
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

		return user;
	}
}
