package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bean.OwnedVoucher;

public class OwnedVoucherDao extends Dao {



	/**
	 * getByIdメソッド 所持情報IDを指定して所持商品券情報を取得
	 *
	 * @param id:int
	 * 				所持情報ID
	 * @return 所持商品券クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public OwnedVoucher getById(int id) throws Exception{
		// 商品券インスタンスを初期化
		OwnedVoucher ownedVoucher = new OwnedVoucher();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(
		            "SELECT " +
		            "owned_voucher.id as id, owned_voucher.voucher_id as voucher_id, " +
		            "regd_voucher.description as description, regd_voucher.image_path as image_path, " +
		            "owned_voucher.user_id as user_id, owned_voucher.used_at as used_at, " +
		            "owned_voucher.updated_at as updated_at, owned_voucher.created_at as created_at " +
		            "FROM " +
		            "owned_voucher JOIN regd_voucher ON owned_voucher.voucher_id = regd_voucher.id " +
		            "WHERE owned_voucher.id = ?"
			);

			// IDをバインド
			statement.setInt(1, id);

			// SQLを実行
			ResultSet resultSet = statement.executeQuery();


			if (resultSet.next()) {
				ownedVoucher.setId(resultSet.getInt("id"));
				ownedVoucher.setVoucherId(resultSet.getInt("voucher_id"));
				ownedVoucher.setDescription(resultSet.getString("description"));
				ownedVoucher.setImagePath(resultSet.getString("image_path"));
				ownedVoucher.setUserId(resultSet.getInt("user_id"));

	            // 未使用かどうか確認
	            Timestamp ts = resultSet.getTimestamp("used_at");
	            if (ts != null) {
	            	ownedVoucher.setUsedAt(ts.toLocalDateTime());
	            } else {
	            	ownedVoucher.setUsedAt(null); // 未使用を表す
	            }

	            ownedVoucher.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
	            ownedVoucher.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

			} else {
				// リザルトセットが存在しない場合
				// 利用者インスタンスにnullをセット
				ownedVoucher = null;
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

		return ownedVoucher; // 空リストか商品券一覧
	}


	/**
	 * searchByIdメソッド 利用者IDを指定して商品券をすべて取得
	 *
	 * @param user_id:int
	 * 				利用者ID
	 * @return 商品券クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public List<OwnedVoucher> searchByUserId(int userId) throws Exception{
		// 商品券リストを初期化
		List<OwnedVoucher> ownedVouchers = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(
		            "SELECT " +
		            "owned_voucher.id as id, owned_voucher.voucher_id as voucher_id, " +
		            "regd_voucher.description as description, regd_voucher.image_path as image_path, " +
		            "owned_voucher.user_id as user_id, owned_voucher.used_at as used_at, " +
		            "owned_voucher.updated_at as updated_at, owned_voucher.created_at as created_at " +
		            "FROM " +
		            "owned_voucher JOIN regd_voucher ON owned_voucher.voucher_id = regd_voucher.id " +
		            "WHERE owned_voucher.user_id = ?"
			);

			// 利用者IDをバインド
			statement.setInt(1, userId);

			// SQLを実行
			ResultSet resultSet = statement.executeQuery();

			// 複数件をリストに追加
	        while (resultSet.next()) {
	        	OwnedVoucher ownedVoucher = new OwnedVoucher();
	        	ownedVoucher.setId(resultSet.getInt("id"));
				ownedVoucher.setVoucherId(resultSet.getInt("voucher_id"));
				ownedVoucher.setDescription(resultSet.getString("description"));
				ownedVoucher.setImagePath(resultSet.getString("image_path"));
				ownedVoucher.setUserId(resultSet.getInt("user_id"));

	            // 未使用かどうか確認
	            Timestamp ts = resultSet.getTimestamp("used_at");
	            if (ts != null) {
	            	ownedVoucher.setUsedAt(ts.toLocalDateTime());
	            } else {
	            	ownedVoucher.setUsedAt(null); // 未使用を表す
	            }

	            ownedVoucher.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
	            ownedVoucher.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

	            ownedVouchers.add(ownedVoucher);
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

		return ownedVouchers; // 空リストか商品券一覧
	}

	/**
	 * 商品券を使用済みに更新する
	 * @param id 所持情報ID
	 * @return 更新成功なら true
	 * @throws Exception
	 */
	public boolean update(int id) throws Exception {
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    int count = 0;

	    try {
	        statement = connection.prepareStatement(
	            "UPDATE owned_voucher SET used_at = CURRENT_TIMESTAMP, updated_at = CURRENT_TIMESTAMP WHERE id = ?"
	        );
	        statement.setInt(1, id);
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
	     * @param voucherId 登録商品券ID
	     * @param userId 利用者ID
	     * @return 登録成功なら true
	     * @throws Exception
	     */
	    public boolean insert(int voucherId, int userId) throws Exception {
	        Connection connection = getConnection();
	        PreparedStatement statement = null;
	        int count = 0;

	        try {
	            statement = connection.prepareStatement(
	            		"INSERT INTO ownedVoucher (voucher_id, user_id) " +
	            	    "VALUES (?, ?, ?)"
	            );

	            statement.setInt(1, voucherId);
	            statement.setInt(1, userId);

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
