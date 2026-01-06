package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import bean.Operator;

public class OperatorDao extends Dao {

    /* =========================
       単一取得
       ========================= */
	/**
	 * getByIdメソッド 利用者IDを指定して利用者インスタンスを1件取得する
	 *
	 * @param id:int
	 *            利用者ID
	 * @return 利用者クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
    public Operator getById(int id) throws Exception {

    	Operator operator = new Operator();

        Connection connection = getConnection();

        PreparedStatement statement = null;


        try {
        	statement = connection.prepareStatement("SELECT id, password, is_admin, is_first_login_completed, is_enable, updated_at, created_at "
        			+ "FROM operator WHERE id = ?");

        	// プリペアードステートメントに教員IDをバインド
        	statement.setInt(1, id);

        	// プリペアードステートメントを実行
        	ResultSet resultSet = statement.executeQuery();

        	if (resultSet.next()) {

        		operator.setId(resultSet.getInt("id"));
        		operator.setPassword(resultSet.getString("password"));
        		operator.setAdmin(resultSet.getBoolean("is_admin"));
        		operator.setFirstLoginCompleted(resultSet.getBoolean("is_first_login_completed"));
        		operator.setEnable(resultSet.getBoolean("is_enable"));
        		operator.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
        		operator.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

        	} else {
				// リザルトセットが存在しない場合
				// 利用者インスタンスにnullをセット
				operator = null;
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
        return operator;
    }

    /* =========================
       新規運営者アカウントを登録():boolean
       ========================= */
    /**
	 *
	 *
	 * @param
	 *
	 * @return
	 * @throws
	 */
    public Pair<Boolean, Integer> insert(String password, boolean isAdmin) throws Exception {

        // コネクションを取得
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;
        int operatorId = -1;
        try {
            // SQL文を準備（created_at / updated_at はDB側で現在時刻をセット）
            statement = connection.prepareStatement(
                "INSERT INTO operator (password, is_admin) " +
                "VALUES (?, ?)",
	            Statement.RETURN_GENERATED_KEYS
            );

            // パラメータをバインド
            statement.setString(1, password);
            statement.setBoolean(2, isAdmin);

            // 実行（INSERTなので executeUpdate）
            count = statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();

	        if (keys.next()) {
	            operatorId = keys.getInt(1);
	        } else {
	            throw new Exception("運営者IDの取得に失敗しました");
	        }

        } catch (Exception e) {
            throw e;
        } finally {
            // PreparedStatement を閉じる
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }

            // Connection を閉じる
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        // 成功したら true を返す
        return Pair.of(count > 0, operatorId);
    }



    /* =========================
       ログイン認証():boolean
       ========================= */

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
	public Operator login(int id, String password) throws Exception {
		// オペレーターのインスタンスを取得
		Operator operator = getById(id);
		// オペレーターがnullまたはパスワードが一致しない場合
		if (operator == null || !operator.getPassword().equals(password)) {
			return null;
		}
		return operator;
	}



    /* =========================
       新しいパスワードを登録():boolean
       ========================= */

	/**
	 *
	 *
	 * @param
	 *
	 * @return
	 * @throws
	 */

	public boolean update(Operator operator) throws Exception {

		// コネクションを取得
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
        	// SQL文を準備
            statement = connection.prepareStatement(
                "UPDATE operator SET password = ?, is_admin = ?, is_first_login_completed = ?,"
                + "is_enable = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?"
            );

            statement.setString(1, operator.getPassword());
            statement.setBoolean(2, operator.isAdmin());
            statement.setBoolean(3, operator.isFirstLoginCompleted());
            statement.setBoolean(4, operator.isEnable());
            statement.setInt(5, operator.getId());

            // 実行（INSERTなので executeUpdate）
            count = statement.executeUpdate();


        } catch (Exception e) {
            throw e;
        } finally {
            // PreparedStatement を閉じる
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }

            // Connection を閉じる
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        return count > 0;

	}


    /* =========================
       全ての運営者アカウント一覧の取得():list<operator>
       ========================= */

	/**
	 *
	 *
	 * @param
	 *
	 * @return
	 * @throws
	 */

	public List<Operator> getAll(Operator Operator) throws Exception {

		// リストを初期化
		List<Operator> list = new ArrayList<>();
		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("SELECT * FROM operator ORDER BY id ASC");

			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Operator operator = new Operator();

				operator.setId(resultSet.getInt("id"));
				operator.setPassword(resultSet.getString("password"));
        		operator.setAdmin(resultSet.getBoolean("is_admin"));
        		operator.setFirstLoginCompleted(resultSet.getBoolean("is_first_login_completed"));
        		operator.setEnable(resultSet.getBoolean("is_enable"));
        		operator.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
        		operator.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

        		list.add(operator);

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


    /* =========================
       対象の運営者アカウントの無効化を登録():boolean
       ========================= */

}





