package dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.commons.lang3.tuple.Pair;

import bean.User;

public class UserDao extends Dao {

	/*
	 * 処理一覧
	 * ・IDで利用者情報を取得(id:int) : User - Get(id:int)
	 * ・利用者情報を更新(User:user) : boolean - Save(User:user) - 新規登録・使用中御朱印帳変更・御朱印カウント・ランクの更新をこれで行う
	 * ・ポイント付与(User:user, point:int) : boolean - AddPoint(User:user, point:int)
	 * ・ポイント消費(User:user, point:int) : boolean - SubPoint(User:user, point:int)
	 * ・ログインする(telNumber:String, password:String) : User - login(telNumber:String, password:String)
	 */

	/**
	 * getByIdメソッド 利用者IDを指定して利用者インスタンスを1件取得する
	 *
	 * @param id:int
	 *            利用者ID
	 * @return 利用者クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public User getById(int id) throws Exception {
		// 利用者インスタンスを初期化
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
				// 利用者インスタンスに検索結果をセット
				user.setId(resultSet.getInt("id"));
				user.setUserName(resultSet.getString("user_name"));
				user.setRealName(resultSet.getString("real_name"));
				user.setBirthDate(resultSet.getTimestamp("real_name").toLocalDateTime());
				user.setAddress(resultSet.getString("address"));
				user.setTelNumber(resultSet.getString("tel_number"));
				user.setPassword(resultSet.getString("password"));
				user.setActiveGoshuinBook(goshuinBookDao.getById(resultSet.getInt("active_goshuin_book_id")));
				user.setPoint(resultSet.getInt("point"));
				user.setRank(resultSet.getInt("rank"));
				user.setGoshuinCount(resultSet.getInt("goshuin_count"));
				user.setProfileImagePath(resultSet.getString("profile_image_path"));
				user.setMyGoshuinBook(goshuinBookDao.getById(resultSet.getInt("my_goshuin_book_id")));
				user.setMyGoshuinBookPublic(resultSet.getBoolean("is_my_goshuin_book_public"));
				user.setLastLoginAt(resultSet.getTimestamp("last_login_at").toLocalDateTime());
				user.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
				user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
			} else {
				// リザルトセットが存在しない場合
				// 利用者インスタンスにnullをセット
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

	/**
	 * getByTelメソッド 利用者電話番号を指定して利用者インスタンスを1件取得する
	 *
	 * @param telNumber:String
	 *            利用者電話番号
	 * @return 利用者クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public User getByTel(String telNumber) throws Exception {
		// 教員インスタンスを初期化
		User user = new User();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT id, user_name, real_name, birth_date, address, tel_number, password, active_goshuin_book_id, point, rank, goshuin_count, profile_image_path, my_goshuin_book_id, is_my_goshuin_book_public, last_login_at, updated_at, created_at"
					+ "FROM user WHERE tel_number = ?");
			// プリペアードステートメントに教員IDをバインド
			statement.setString(1, telNumber);
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
				user.setActiveGoshuinBook(goshuinBookDao.getById(resultSet.getInt("active_goshuin_book_id")));
				user.setPoint(resultSet.getInt("point"));
				user.setRank(resultSet.getInt("rank"));
				user.setGoshuinCount(resultSet.getInt("goshuin_count"));
				user.setProfileImagePath(resultSet.getString("profile_image_path"));
				user.setMyGoshuinBook(goshuinBookDao.getById(resultSet.getInt("my_goshuin_book_id")));
				user.setMyGoshuinBookPublic(resultSet.getBoolean("is_my_goshuin_book_public"));
				user.setLastLoginAt(resultSet.getTimestamp("last_login_at").toLocalDateTime());
				user.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
				user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
			} else {
				// リザルトセットが存在しない場合
				// 利用者インスタンスにnullをセット
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

	/**
	 * insertメソッド 利用者情報を登録 御朱印帳発行も自動で行う
	 *
	 * @param user:User
	 *            利用者情報
	 * @return 成功可否
	 * @throws Exception
	 */
	public boolean insert(User user) throws Exception {
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		// 実行件数
		int count = 0;

		try {

			// 利用者を新規作成

			// 新たな御朱印帳を発行
			// 御朱印帳DAOを初期化
			GoshuinBookDao goshuinBookDao = new GoshuinBookDao();
			// 御朱印帳登録
			Pair<Boolean, Integer> pair = goshuinBookDao.insert(user.getId());
			// 登録に失敗した場合、例外を発生させる
			if (!pair.getLeft()) {
				throw new Exception();
			}


			// プリペアードステートメントにINSERT文をセット
			statement = connection.prepareStatement("INSERT INTO user(user_name, real_name, birth_date, address, tel_number, password, active_goshuin_book_id, my_goshuin_book_id, last_login_at) VALUES(?, ?, ?, ?, ?, ?, ?, ?, CURRENT_DATETIME)");
			// プリペアードステートメントに値をバインド
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getRealName());
			statement.setTimestamp(3, Timestamp.valueOf(user.getBirthDate()));
			statement.setString(4, user.getAddress());
			statement.setString(5, user.getTelNumber());
			statement.setString(6, user.getPassword());
			statement.setInt(7, pair.getRight());
			statement.setInt(8, pair.getRight());


			// プリペアードステートメントを実行
			count = statement.executeUpdate();

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


	/**
	 * updateメソッド 利用者情報を更新
	 *
	 * @param user:User
	 *            利用者情報
	 * @return 成功可否
	 * @throws Exception
	 */
	public boolean update(User user) throws Exception {
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		// 実行件数
		int count = 0;

		try {

			// 利用者が存在した場合、情報を更新
			// プリペアードステートメントにUPDATE文をセット
			statement = connection.prepareStatement("UPDATE user SET user_name = ?, active_goshuin_book_id = ?, rank = ?, goshuin_count = ?, profile_image_path = ?, my_goshuin_book_id = ?, is_my_goshuin_book_public = ?, updated_at = CURRENT_DATETIME WHERE id = ?");
			// プリペアードステートメントに値をバインド
			statement.setString(1, user.getUserName());
			statement.setInt(2, user.getActiveGoshuinBook().getId());
			statement.setInt(3, user.getRank());
			statement.setInt(4, user.getGoshuinCount());
			statement.setString(5, user.getProfileImagePath());
			statement.setInt(6, user.getMyGoshuinBook().getId());
			statement.setBoolean(7, user.isMyGoshuinBookPublic());
			statement.setInt(8, user.getId());

			// プリペアードステートメントを実行
			count = statement.executeUpdate();

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

	/**
	 * addPointメソッド ポイントを付与
	 *
	 * @param user:User
	 *            利用者情報
	 *        point:Int
	 *            付与するポイント量
	 * @return 成功可否
	 * @throws Exception
	 */
	public boolean addPoint(User user, int point) throws Exception {
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		// 実行件数
		int count = 0;

		try {
			// データベースから利用者を取得
			User old = getById(user.getId());

			if (old == null) {
				// 利用者が存在しなかった場合、例外を発生させる

				throw new Exception();

			} else {
				// 利用者が存在した場合、ポイントを付与
				// プリペアードステートメントにUPDATE文をセット
				statement = connection.prepareStatement("UPDATE user SET point = ?, updated_at = CURRENT_DATETIME WHERE id = ?");
				// プリペアードステートメントに値をバインド
				statement.setInt(1, user.getPoint() + point);
				statement.setInt(2, user.getId());
			}

			// プリペアードステートメントを実行
			count = statement.executeUpdate();

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

	/**
	 * subPointメソッド ポイントを消費
	 *
	 * @param user:User
	 *            利用者情報
	 *        point:Int
	 *            消費するポイント量
	 * @return 成功可否
	 * @throws Exception
	 */
	public boolean subPoint(User user, int point) throws Exception {
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		// 実行件数
		int count = 0;

		try {
			// データベースから利用者を取得
			User old = getById(user.getId());

			if (old == null) {
				// 利用者が存在しなかった場合、例外を発生させる

				throw new Exception();

			} else {
				// 利用者が存在した場合、ポイントを付与
				// プリペアードステートメントにUPDATE文をセット
				statement = connection.prepareStatement("UPDATE user SET point = ?, updated_at = CURRENT_DATETIME WHERE id = ?");
				// プリペアードステートメントに値をバインド
				statement.setInt(1, user.getPoint() - point);
				statement.setInt(2, user.getId());
			}

			// プリペアードステートメントを実行
			count = statement.executeUpdate();

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

	/**
	 * loginメソッド 利用者の電話番号とパスワードで認証する
	 *
	 * @param telNumber:String
	 *            電話番号
	 * @param password:String
	 *            パスワード
	 * @return 認証成功:利用者クラスのインスタンス, 認証失敗:null
	 * @throws Exception
	 */
	public User login(String telNumber, String password) throws Exception {
		// 利用者クラスのインスタンスを取得
		User user = getByTel(telNumber);
		// 利用者がnullまたはパスワードが一致しない場合
		if (user == null || !user.getPassword().equals(password)) {
			return null;
		}
		return user;
	}
}
