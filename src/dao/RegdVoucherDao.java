package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.RegdVoucher;

public class RegdVoucherDao extends Dao {

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
	public RegdVoucher getById(int id) throws Exception{
		// 商品券インスタンスを初期化
		RegdVoucher regdVoucher = new RegdVoucher();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(
		            "SELECT id, description, image_path, used_at, created_at"
		            +" FROM regd_voucher WHERE id = ?"
			);

			// 利用者IDをバインド
			statement.setInt(1, id);

			// SQLを実行
			ResultSet resultSet = statement.executeQuery();


			if (resultSet.next()) {
				regdVoucher.setId(resultSet.getInt("id"));
	            regdVoucher.setDescription(resultSet.getString("description"));
	            regdVoucher.setImagePath(resultSet.getString("image_path"));

	            regdVoucher.setCreatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
	            regdVoucher.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

			} else {
				// リザルトセットが存在しない場合
				// 利用者インスタンスにnullをセット
				regdVoucher = null;
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

		return regdVoucher; // 空リストか商品券一覧
	}




    /**
     * 商品券を新規登録
     * @param regdVoucher 登録する商品券情報
     * @return 登録成功なら true
     * @throws Exception
     */
    public boolean insert(RegdVoucher regdVoucher) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            statement = connection.prepareStatement(
            		"INSERT INTO voucher (id, description, image_path) " +
            	    "VALUES (?, ?, ?)"
            );

            statement.setInt(1, regdVoucher.getId());
            statement.setString(2, regdVoucher.getDescription());
            statement.setString(3, regdVoucher.getImagePath());

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
