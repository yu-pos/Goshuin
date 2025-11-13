package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.Review;

public class ReviewDao extends Dao {

	//口コミ情報を登録(review:Review):boolean
	//対象の神社仏閣の口コミいちらんを取得():List<review>
	//対象の口コミのいいねカウントを更新():boolean
	 public boolean insert(Review review ) throws Exception {
	        // コネクションを確立
	        Connection connection = getConnection();
	        // プリペアードステートメント
	        PreparedStatement statement = null;
	        // 実行件数
	        int count = 0;

	        try {
	            // SQL文を準備
	            String sql = "INSERT INTO review " +
	                         "(id,shrine_and_temple_id,user_id,text,imagePath) " +
	                         "VALUES (?, ?, ?,?,?)";

	            statement = connection.prepareStatement(sql);

	            // プレースホルダに値をバインド
	            statement.setInt(1, review.getId());
	            statement.setInt(2, review.getShrineAndTempleId());
	            statement.setInt(3, review.getUserId());
	            statement.setString(4, review.getText());
	            statement.setString(5, review.getImagePath());



	            // SQLを実行
	            count = statement.executeUpdate();

	            // 1件以上登録できれば true
	            
	        } catch (Exception e) {
	            throw e; // 呼び出し元に例外を投げる
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

	 

		if (count == 1) {
			// 実行件数1件の場合
			return true;
		} else {
			// 実行件数がそれ以外の場合
			return false;
		}
	 }
}


	 
