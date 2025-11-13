package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.OwnedGoshuin;
import bean.User;



public class OwnedGoshuinDao extends Dao {

	//利用者の購入済み御朱印一覧を取得():list<OwnedGoshuin>
	//所持御朱印に購入した御朱印情報を登録():boolean
	//利用者の現在使用中の御朱印帳に登録されている御朱印数を取得():list<int>
	//利用者の使用中御朱印に登録されている御朱印を取得(userId:int):List<goshuinBook>
	//



	public List<OwnedGoshuin> SearchByUser(User user) {



		// リストを初期化
		List<OwnedGoshuin> list = new ArrayList<>();
		// データベースへのコネクションを確立

		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT * from owned_goshuin where user_id = ?");
			// プリペアードステートメントに御朱印帳IDをバインド
			statement.setInt(1, user.getId());
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();



			// Daoを初期化
	        RegdGoshuinDao regdGoshuinDao = new RegdGoshuinDao();

			while(resultSet.next())  {
				// リザルトセットが存在する場合
				// 御朱印帳インスタンスに検索結果をセット
				//GoshuinBookStickerAttachment goshuinBookStickerAttachment = new GoshuinBookStickerAttachment();
                OwnedGoshuin ownedGoshuin = new OwnedGoshuin();


                /*
                 * 何をしたいのか？
                 *
                 * 　購入済みの御朱印一覧がほしい
                 * 　御朱印の詳細情報もほしい
                 * 　-> 上記２つのデータを含んだbeanのリストがほしい
                 *
                 * ※現在フロー
                 * 　１．購入済み御朱印の一覧を取得
                 * 　２．各御朱印の詳細を取得する
                 *
                 * OwnedGoshuin(bean)のsetなんちゃらを全て書く
                 * ( )内にresultSet.get型(カラム名)を書く
                 * ※型とカラム名はテーブル設計書を見るべし！
                 *
                 */

                ownedGoshuin.setUserId(resultSet.getInt("user_id"));





                // 御朱印詳細データ取得
                ownedGoshuin.setGoshuin(ownedGoshuinDao.getById(resultSet.getInt("id")));

                ownedGoshuin.setGoshuinBookId(resultSet.getInt("goshuin_book_id"));

                ownedGoshuin.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                ownedGoshuin.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());




//				ownedGoshuin.setGoshuin(regdGoshuinDao.getById(resultSet.getInt("goshuin_id")));
//				ownedGoshuin.setUserId(regdGoshuinDao.getUserId(resultSet.getId("user_id"));
//				goshuinBookStickerAttachment.setxPos(resultSet.getDouble("x_pos"));
//				goshuinBookStickerAttachment.setyPos(resultSet.getDouble("y_pos"));
//				goshuinBookStickerAttachment.setRotation(resultSet.getDouble("rotation"));
//				goshuinBook.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
//				goshuinBook.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
//
//
//
//				list.add(goshuinBookStickerAttachment);


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



		/**
		 * 登録用のsaveメソッド
		 * @param classNum
		 * @return 実行可否
		 * @throws Exception
		 */
	  public boolean insert(OwnedGoshuin ownedGoshuin ) throws Exception {
	        // コネクションを確立
	        Connection connection = getConnection();
	        // プリペアードステートメント
	        PreparedStatement statement = null;
	        // 実行件数
	        int count = 0;

	        try {
	            // SQL文を準備
	            String sql = "INSERT INTO owned_goshuin " +
	                         "(user_id, id, goshuin_book_id ) " +
	                         "VALUES (?, ?, ?)";

	            statement = connection.prepareStatement(sql);

	            // プレースホルダに値をバインド
	            statement.setInt(1, ownedGoshuin.getUserId());
	            statement.setInt(2, ownedGoshuin.getId());
	            statement.setInt(3, ownedGoshuin.getGoshuinBookId());



	            // SQLを実行
	            count = statement.executeUpdate();

	            // 1件以上登録できれば true
	            return count > 0;
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




//public List<OwnedGoshuin> SearchByGoshuinBook(User user) {



	// リストを初期化
//	List<OwnedGoshuin> list = new ArrayList<>();
	// データベースへのコネクションを確立

//	Connection connection = getConnection();
	// プリペアードステートメント
//	PreparedStatement statement = null;

//	try {
		// プリペアードステートメントにSQL文をセット
//		statement = connection.prepareStatement("SELECT * from owned_goshuin where user_id = ?");
		// プリペアードステートメントに御朱印帳IDをバインド
//		statement.setInt(1, user.getId());
		// プリペアードステートメントを実行
//		ResultSet resultSet = statement.executeQuery();



		// Daoを初期化
 //       RegdGoshuinDao regdGoshuinDao = new RegdGoshuinDao();

//	while(resultSet.next())  {
			// リザルトセットが存在する場合
			// 御朱印帳インスタンスに検索結果をセット
			//GoshuinBookStickerAttachment goshuinBookStickerAttachment = new GoshuinBookStickerAttachment();
   //         OwnedGoshuin ownedGoshuin = new OwnedGoshuin();


            /*
             * 何をしたいのか？
             *
             * 　購入済みの御朱印一覧がほしい
             * 　御朱印の詳細情報もほしい
             * 　-> 上記２つのデータを含んだbeanのリストがほしい
             *
             * ※現在フロー
             * 　１．購入済み御朱印の一覧を取得
             * 　２．各御朱印の詳細を取得する
             *
             * OwnedGoshuin(bean)のsetなんちゃらを全て書く
             * ( )内にresultSet.get型(カラム名)を書く
             * ※型とカラム名はテーブル設計書を見るべし！
             *
             */

      //      ownedGoshuin.setUserId(resultSet.getInt("user_id"));
//




            // 御朱印詳細データ取得
     //      ownedGoshuin.setGoshuin(ownedGoshuinDao.getById(resultSet.getInt("id")));

     //       ownedGoshuin.setGoshuinBookId(resultSet.getInt("goshuin_id"));









	//	}
//	} catch (Exception e) {
//		throw e;
//	} finally {
		// プリペアードステートメントを閉じる
//		if (statement != null) {
	//		try {
	//			statement.close();
	//		} catch (SQLException sqle) {
	//			throw sqle;
	//		}
//		}
		// コネクションを閉じる
	//	if (connection != null) {
	//		try {
	//			connection.close();
	//		} catch (SQLException sqle) {
	//			throw sqle;
//			}
//		}
	//}

//	return list;
//}
//	        }


//


public List<OwnedGoshuin> SearchByGoshuinBook(int GoshuinBookId) throws Exception {



	// リストを初期化
	List<OwnedGoshuin> list = new ArrayList<>();
	// データベースへのコネクションを確立

	Connection connection = getConnection();
	// プリペアードステートメント
	PreparedStatement statement = null;

	try {
		// プリペアードステートメントにSQL文をセット
		statement = connection.prepareStatement("SELECT * from owned_goshuin where goshuin_book_id = ?");
		// プリペアードステートメントに御朱印帳IDをバインド
		statement.setInt(1, GoshuinBookId);
		// プリペアードステートメントを実行
		ResultSet resultSet = statement.executeQuery();



		// Daoを初期化
        RegdGoshuinDao regdGoshuinDao = new RegdGoshuinDao();

		while(resultSet.next())  {
			// リザルトセットが存在する場合
			// 御朱印帳インスタンスに検索結果をセット
			//GoshuinBookStickerAttachment goshuinBookStickerAttachment = new GoshuinBookStickerAttachment();
            OwnedGoshuin ownedGoshuin = new OwnedGoshuin();


            /*
             * 何をしたいのか？
             *
             * 　購入済みの御朱印一覧がほしい
             * 　御朱印の詳細情報もほしい
             * 　-> 上記２つのデータを含んだbeanのリストがほしい
             *
             * ※現在フロー
             * 　１．購入済み御朱印の一覧を取得
             * 　２．各御朱印の詳細を取得する
             *
             * OwnedGoshuin(bean)のsetなんちゃらを全て書く
             * ( )内にresultSet.get型(カラム名)を書く
             * ※型とカラム名はテーブル設計書を見るべし！
             *
             */


            ownedGoshuin.setUserId(resultSet.getInt("user_id"));






            // 御朱印詳細データ取得
            ownedGoshuin.setGoshuin(regdGoshuinDao.getById(resultSet.getInt("id")));

            ownedGoshuin.setGoshuinBookId(resultSet.getInt("goshuin_book_id"));

            ownedGoshuin.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
            ownedGoshuin.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            list.add(ownedGoshuin);
		}
	} catch (Exception e) {
		throw e;
	} finally {
		// プリペアードステートメントを閉じる
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

}
