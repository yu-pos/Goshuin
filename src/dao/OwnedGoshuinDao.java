package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import bean.User;
import bean.GoshuinBookStickerAttachment;
import bean.OwnedGoshuin;



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
				GoshuinBookStickerAttachment goshuinBookStickerAttachment = new GoshuinBookStickerAttachment();
                OwnedGoshuin ownedGoshuin = new OwnedGoshuin();

				ownedGoshuin.setGoshuin(regdGoshuinDao.getById(resultSet.getInt("goshuin_id")));
				ownedGoshuin.setUserId(regdGoshuinDao.getById("goshuin_book_sticker_id"));
				goshuinBookStickerAttachment.setxPos(resultSet.getDouble("x_pos"));
				goshuinBookStickerAttachment.setyPos(resultSet.getDouble("y_pos"));
				goshuinBookStickerAttachment.setRotation(resultSet.getDouble("rotation"));
				goshuinBook.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
				goshuinBook.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

				list.add(goshuinBookStickerAttachment);

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
		public boolean save(ClassNum classNum) throws Exception {

			// コネクションを確立
			Connection connection = getConnection();
			// プリペアードステートメント
			PreparedStatement statement = null;
			// 実行件数
			int count = 0;

			try {
				// プリペアードステートメントにINSERT文をセット
				statement = connection.prepareStatement("insert into class_num(school_cd, class_num) values(?, ?)");
				// プリペアードステートメントに値をバインド
				statement.setString(1, classNum.getSchool().getCd());
				statement.setString(2, classNum.getClass_num());
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

			if (count > 0) {
				// 実行件数が1件以上ある場合
				return true;
			} else {
				// 実行件数が0件の場合
				return false;
			}
		}






	return
	}
}