package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.OwnedGoshuinBookDesignGroup;

public class OwnedGoshuinBookDesignGroupDao extends Dao {


	/*
	 * 処理一覧
	 * ・対象の利用者の所持御朱印帳デザイングループ一覧を取得(userId:int) : List<OwnedGoshuinBookDesignGroup> - SearchByUser(user:User)
	 * ・対象の利用者の所持御朱印帳デザイングループ情報を登録(OwnedGoshuinBookDesignGroup : ownedGoshuinBookDesignGroup) : boolean
	 *
	 */

	/**
	 * getByUserIdメソッド 利用者の所持デザイングループ情報を取得
	 *
	 * @param userId:int
	 *            利用者ID
	 * @return 所持デザイングループリスト
	 * @throws Exception
	 */
	public List<OwnedGoshuinBookDesignGroup> getByUser(int userId) throws Exception {
		// リストを初期化
		List<OwnedGoshuinBookDesignGroup> list = new ArrayList<>();
		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;



		try {
			statement = connection.prepareStatement("SELECT * FROM owned_goshuin_book_design_group WHERE user_id = ?");
			// プリペアードステートメントに御朱印帳IDをバインド
			statement.setInt(1, userId);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			RegdGoshuinBookDesignGroupDao regdGoshuinBookDesignGroupDao = new RegdGoshuinBookDesignGroupDao();

			while(resultSet.next()) {

				OwnedGoshuinBookDesignGroup ownedGoshuinBookDesignGroup = new OwnedGoshuinBookDesignGroup();

				ownedGoshuinBookDesignGroup.setGoshuinBookDesignGroup(regdGoshuinBookDesignGroupDao.getById(resultSet.getInt("goshuin_book_design_group_id")));
				ownedGoshuinBookDesignGroup.setUserId(resultSet.getInt("user_id"));
				ownedGoshuinBookDesignGroup.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
				ownedGoshuinBookDesignGroup.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

				list.add(ownedGoshuinBookDesignGroup);
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
	 * insertメソッド 所持デザイングループ情報を登録
	 *
	 * @param ownedGoshuinBookDesignGroup:OwnedGoshuinBookDesignGroup
	 *            所持デザイングループ情報
	 * @return 成功可否
	 * @throws Exception
	 */
    public boolean insert(OwnedGoshuinBookDesignGroup ownedGoshuinBookDesignGroup) throws Exception {

        // コネクションを取得
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            // SQL文を準備（created_at / updated_at はDB側で現在時刻をセット）
            statement = connection.prepareStatement(
                "INSERT INTO owned_goshuin_book_design_group (goshuin_group_design_group_id, user_id) " +
                "VALUES (?, ?)"
            );

            // パラメータをバインド
            statement.setInt(1, ownedGoshuinBookDesignGroup.getGoshuinBookDesignGroup().getId());
            statement.setInt(2, ownedGoshuinBookDesignGroup.getUserId());

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
}
