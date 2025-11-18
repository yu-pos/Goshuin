package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	                         "(shrine_and_temple_id,user_id,text,imagePath) " +
	                         "VALUES ( ?, ?,?,?)";

	            statement = connection.prepareStatement(sql);

	            // プレースホルダに値をバインド
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


	public Review getById(int reviewId) throws Exception {

		//reviewを宣言
		Review review = new Review();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT * FROM review WHERE id=?");
			// プリペアードステートメントに神社仏閣IDをバインド
			statement.setInt(1, reviewId);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();


			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// レビューインスタンスに検索結果をセット

	            review.setId(resultSet.getInt("id"));
	            review.setUserId(resultSet.getInt("user_id"));
	            review.setShrineAndTempleId(resultSet.getInt("shrine_and_temple_id"));
	            review.setImagePath(resultSet.getString("image_path"));
	            review.setText(resultSet.getString("text"));
	            review.setLikeCount(resultSet.getInt("like_count"));

			} else {
				// リザルトセットが存在しない場合
				// レビューインスタンスにnullを


				review = null;
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

		return review;
	}


	public List<Review> searchByShrineAndTempleId(int shrineAndTempleId) throws Exception{
		// 商品券リストを初期化
		List<Review> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(
		            "SELECT  id,shrine_and_temple_id,user_id,text,image_path, like_count"
		            +"FROM review WHERE shurain_and_temple_id = ?"
			);

			// 神社仏閣IDをバインド
			statement.setInt(1, shrineAndTempleId);

			// SQLを実行
			ResultSet resultSet = statement.executeQuery();

			// 複数件をリストに追加
	        while (resultSet.next()) {
	            Review review = new Review();
	            review.setId(resultSet.getInt("id"));
	            review.setUserId(resultSet.getInt("user_id"));
	            review.setShrineAndTempleId(resultSet.getInt("shrine_and_temple_id"));
	            review.setImagePath(resultSet.getString("image_path"));
	            review.setText(resultSet.getString("text"));
	            review.setLikeCount(resultSet.getInt("like_count"));

	            list.add(review);

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

		return list; // 空リストか商品券一覧
	}



	public boolean update(Review review) throws Exception {
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		// 実行件数
		int count = 0;

		try {

			// 利用者が存在した場合、情報を更新
			// プリペアードステートメントにUPDATE文をセット
			statement = connection.prepareStatement("UPDATE review SET shrine_and_temple_id = ?,  user_id = ?, text= ?, "
					+ "image_path = ?, like_count = ?,  updated_at = CURRENT_DATETIME WHERE id = ?");
			// プリペアードステートメントに値をバインド
			statement.setInt(1, review.getShrineAndTempleId());
			statement.setInt(2, review.getUserId());
			statement.setString(3, review.getText());
			statement.setString(4, review.getImagePath());
			statement.setInt(5, review.getLikeCount());
			statement.setInt(6, review.getId());

			
			


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
}
