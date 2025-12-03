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
	            statement.setInt(1, review.getShrineAndTempleId());
	            statement.setInt(2, review.getUserId());
	            statement.setString(3, review.getText());
	            statement.setString(4, review.getImagePath());



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
			statement = connection.prepareStatement(
		            "SELECT review.id AS review_id, review.shrine_and_temple_id AS shrine_and_temple_id,"
		            + " review.user_id AS user_id, user.user_name AS user_name, user.profile_image_path AS user_image_path, review.text AS text, "
		            + " review.image_path AS image_path,"
		            + " (SELECT COUNT(*) FROM review_like WHERE review_id = ?) AS like_count, "
		            + " review.updated_at AS updated_at, review.created_at AS created_at"
		            + " FROM review"
		            + " JOIN user ON review.user_id = user.id "
		            + " WHERE review.id = ?"
			);
			// プリペアードステートメントに神社仏閣IDをバインド
			statement.setInt(1, reviewId);
			statement.setInt(2, reviewId);

			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();


			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// レビューインスタンスに検索結果をセット

	            review.setId(resultSet.getInt("review_id"));
	            review.setUserId(resultSet.getInt("user_id"));
	            review.setUserName(resultSet.getString("user_name"));
	            review.setUserImagePath(resultSet.getString("user_image_path"));
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
		            "SELECT review.id AS review_id, review.shrine_and_temple_id AS shrine_and_temple_id,"
		            + " review.user_id AS user_id, user.user_name AS user_name, user.profile_image_path AS user_image_path, review.text AS text, "
		            + " review.image_path AS image_path, "
		            + " (SELECT COUNT(*) FROM review_like WHERE review_id = review.id) AS like_count, "
		            + " review.updated_at AS updated_at, review.created_at AS created_at"
		            + " FROM review"
		            + " JOIN user ON review.user_id = user.id "
		            + " WHERE review.shrine_and_temple_id = ?"
			);

			// 神社仏閣IDをバインド
			statement.setInt(1, shrineAndTempleId);

			// SQLを実行
			ResultSet resultSet = statement.executeQuery();

			// 複数件をリストに追加
	        while (resultSet.next()) {
	            Review review = new Review();
	            review.setId(resultSet.getInt("review_id"));
	            review.setUserId(resultSet.getInt("user_id"));
	            review.setUserName(resultSet.getString("user_name"));
	            review.setUserImagePath(resultSet.getString("user_image_path"));
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

	//対象ユーザーがいいねをしているレビューIDを取得
	public List<Integer> searchIdBySATIdAndisLiked(int shrineAndTempleId, int userId) throws Exception{
		// 商品券リストを初期化
		List<Integer> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(
		            "SELECT review_id FROM review_like " +
		            "WHERE review_id IN(SELECT id FROM review WHERE shrine_and_temple_id = ?) AND " +
		            "user_id = ?"
			);

			// 神社仏閣IDをバインド
			statement.setInt(1, shrineAndTempleId);
			statement.setInt(2, userId);

			// SQLを実行
			ResultSet resultSet = statement.executeQuery();

			// 複数件をリストに追加
	        while (resultSet.next()) {


	            list.add(resultSet.getInt("review_id"));

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

	public boolean insertLike(int reviewId, int userId) throws Exception {

		// コネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        // 実行件数
        int count = 0;

        try {
            // SQL文を準備
            String sql = "INSERT INTO review_like " +
                         "(review_id, user_id) " +
                         "VALUES (?, ?)";

            statement = connection.prepareStatement(sql);

            // プレースホルダに値をバインド
            statement.setInt(1, reviewId);
            statement.setInt(2, userId);



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

	public boolean deleteLike(int reviewId, int userId) throws Exception {

		// コネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        // 実行件数
        int count = 0;

        try {
            // SQL文を準備
            String sql = "DELETE FROM review_like " +
                         "WHERE review_id = ? AND user_id = ? ";

            statement = connection.prepareStatement(sql);

            // プレースホルダに値をバインド
            statement.setInt(1, reviewId);
            statement.setInt(2, userId);



            // SQLを実行
            count = statement.executeUpdate();

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
