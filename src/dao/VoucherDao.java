package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bean.Voucher;

public class VoucherDao extends Dao {

	//利用者の商品券一覧を取得():list<voucher>

	//使用済み登録(voucherId:int):boolean

	//利用者に商品券を付与():boolean(ランクが上がる方とランクが最大で、御朱印カウントが３０に達する方)



	/**
	 * getByIdメソッド 商品券IDを指定して商品券を取得
	 *
	 * @param id:int
	 * 				商品券ID
	 * @return 商品券クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public Voucher getById(int id) throws Exception{
		// 商品券インスタンスを初期化
		Voucher voucher = new Voucher();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(
		            "SELECT id, user_id, description, image_path, used_at, created_at"
		            +" FROM voucher WHERE id = ?"
			);

			// 利用者IDをバインド
			statement.setInt(1, id);

			// SQLを実行
			ResultSet resultSet = statement.executeQuery();


			if (resultSet.next()) {
				voucher.setId(resultSet.getInt("id"));
	            voucher.setUserId(resultSet.getInt("user_id"));
	            voucher.setDescription(resultSet.getString("description"));
	            voucher.setImagePath(resultSet.getString("image_path"));

	            // 未使用かどうか確認
	            Timestamp ts = resultSet.getTimestamp("used_at");
	            if (ts != null) {
	                voucher.setUsedAt(ts.toLocalDateTime());
	            } else {
	                voucher.setUsedAt(null); // 未使用を表す
	            }

	            voucher.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

			} else {
				// リザルトセットが存在しない場合
				// 利用者インスタンスにnullをセット
				voucher = null;
			}



		} catch (Exception e) {
			throw e;
		} finally {
			// ステートメントを閉じる
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

		return voucher; // 空リストか商品券一覧
	}


	/**
	 * searchByIdメソッド 利用者IDを指定して商品券をすべて取得
	 *
	 * @param user_id:int
	 * 				利用者ID
	 * @return 商品券クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public List<Voucher> searchByUserId(int userId) throws Exception{
		// 商品券リストを初期化
		List<Voucher> vouchers = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(
		            "SELECT id, user_id, description, image_path, used_at, created_at"
		            +" FROM voucher WHERE user_id = ?"
			);

			// 利用者IDをバインド
			statement.setInt(1, userId);

			// SQLを実行
			ResultSet resultSet = statement.executeQuery();

			// 複数件をリストに追加
	        while (resultSet.next()) {
	            Voucher voucher = new Voucher();
	            voucher.setId(resultSet.getInt("id"));
	            voucher.setUserId(resultSet.getInt("user_id"));
	            voucher.setDescription(resultSet.getString("description"));
	            voucher.setImagePath(resultSet.getString("image_path"));

	            // 未使用かどうか確認
	            Timestamp ts = resultSet.getTimestamp("used_at");
	            if (ts != null) {
	                voucher.setUsedAt(ts.toLocalDateTime());
	            } else {
	                voucher.setUsedAt(null); // 未使用を表す
	            }

	            voucher.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

	            vouchers.add(voucher);
	        }


		} catch (Exception e) {
			throw e;
		} finally {
			// ステートメントを閉じる
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

		return vouchers; // 空リストか商品券一覧
	}

	/**
	 * 商品券を使用済みに更新する
	 * @param id 商品券ID
	 * @return 更新成功なら true
	 * @throws Exception
	 */
	public boolean update(int voucherId) throws Exception {
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    int count = 0;

	    try {
	        statement = connection.prepareStatement(
	            "UPDATE voucher SET used_at = CURRENT_TIMESTAMP, updated_at = CURRENT_TIMESTAMP WHERE id = ?"
	        );
	        statement.setInt(1, voucherId);
	        count = statement.executeUpdate();
	    } finally {
	      	// ステートメントを閉じる
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
	    return count > 0;
	}


	    /**
	     * 利用者に商品券を付与
	     * @param userId 利用者ID
	     * @param description 商品券の説明(付与理由、金額など)
	     * @param imagePath 画像パス
	     * @return 登録成功なら true
	     * @throws Exception
	     */
	    public boolean insert(Voucher voucher) throws Exception {
	        Connection connection = getConnection();
	        PreparedStatement statement = null;
	        int count = 0;

	        try {
	            statement = connection.prepareStatement(
	            		"INSERT INTO voucher (user_id, description, image_path) " +
	            	    "VALUES (?, ?, ?)"
	            );

	            statement.setInt(1, voucher.getUserId());
	            statement.setString(2, voucher.getDescription());
	            statement.setString(3, voucher.getImagePath());

	            count = statement.executeUpdate();
	            return count > 0;
	        } finally {
	        	// ステートメントを閉じる
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
	    }
}
