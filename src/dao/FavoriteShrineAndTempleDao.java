package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.FavoriteShrineAndTemple;

public class FavoriteShrineAndTempleDao extends Dao {

	//利用者のお気に入り神社仏閣情報を取得:(userId:int)list<FavoriteShrineAndTemple>
	//対象のお気に入り神社仏閣を削除する(favoriteShrineAndTemple:FavoriteShrineAndTemple):boolean
	//神社仏閣情報を登録(favoriteShrineAndTemple:FavoriteShrineAndTemple):boolean

	public List<FavoriteShrineAndTemple> searchByUser(int userId) throws Exception {
		// リストを初期化
		List<FavoriteShrineAndTemple> list = new ArrayList<>();
		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT * from shrine_and_temple_favorite");

			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();


			while(resultSet.next())  {
				// リザルトセットが存在する場合
				// 御朱印帳インスタンスに検索結果をセット
				FavoriteShrineAndTemple favoriteShrineAndTemple = new FavoriteShrineAndTemple();

				favoriteShrineAndTemple.setShrineAndTempleId(resultSet.getInt("shrine_and_temple_id"));
				favoriteShrineAndTemple.setUserId(resultSet.getInt("user_id"));
				favoriteShrineAndTemple.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
				favoriteShrineAndTemple.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

				list.add(favoriteShrineAndTemple);

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


    public boolean insert(FavoriteShrineAndTemple favoriteShrineAndTemple) throws Exception {

        // コネクションを取得
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            // SQL文を準備（created_at / updated_at はDB側で現在時刻をセット）
            statement = connection.prepareStatement(
                "INSERT INTO shrine_and_temple_favorite(shrine_and_temple_id, user_id) " +
                "VALUES (?, ?)"
            );

            // パラメータをバインド
            statement.setInt(1, favoriteShrineAndTemple.getShrineAndTempleId());
            statement.setInt(2, favoriteShrineAndTemple.getUserId());

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

		if (count == 1) {
			// 実行件数1件の場合
			return true;
		} else {
			// 実行件数がそれ以外の場合
			return false;
		}
    }


    public boolean delete(FavoriteShrineAndTemple favoriteShrineAndTemple) throws Exception {

        // コネクションを取得
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            // SQL文を準備（created_at / updated_at はDB側で現在時刻をセット）
            statement = connection.prepareStatement(
                "DELETE FROM shrine_and_temple_favorite " +
                "WHERE shrine_and_temple_id = ?, user_id = ?"
            );

            // パラメータをバインド
            statement.setInt(1, favoriteShrineAndTemple.getShrineAndTempleId());
            statement.setInt(2, favoriteShrineAndTemple.getUserId());

            // 実行
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

		if (count == 1) {
			// 実行件数1件の場合
			return true;
		} else {
			// 実行件数がそれ以外の場合
			return false;
		}
    }



}
