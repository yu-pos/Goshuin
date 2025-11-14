package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import bean.RegdGoshuinBookDesignGroup;

public class RegdGoshuinBookDesignGroupDao extends Dao {

	/* =========================
    単一取得
    ========================= */

	/**
	 *
	 *
	 * @param
	 *
	 * @return
	 * @throws
	 */

	public RegdGoshuinBookDesignGroup getById(int id) throws Exception {
		// 利用者インスタンスを初期化
		RegdGoshuinBookDesignGroup regdGoshuinBookDesignGroup = new RegdGoshuinBookDesignGroup();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT id, name, updated_at, created_at"
					+ "FROM regd_goshuin_book_design_group WHERE id = ?");
			// プリペアードステートメントにステッカーIDをバインド
			statement.setInt(1, id);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// 利用者インスタンスに検索結果をセット
				regdGoshuinBookDesignGroup.setId(resultSet.getInt("id"));
				regdGoshuinBookDesignGroup.setName(resultSet.getString("name"));
				regdGoshuinBookDesignGroup.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
				regdGoshuinBookDesignGroup.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

			} else {
				// リザルトセットが存在しない場合
				// 利用者インスタンスにnullをセット
				regdGoshuinBookDesignGroup = null;
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

		return regdGoshuinBookDesignGroup;
	}

	/*御朱印帳登録一覧デザイングループ一覧を取得():list<goshuinBookDesignGroup>*/

	/**
	 *
	 *
	 * @param
	 *
	 * @return
	 * @throws
	 */

	public List<RegdGoshuinBookDesignGroup> getAll() throws Exception {


		// リストを初期化
		List<RegdGoshuinBookDesignGroup> list = new ArrayList<>();
		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("SELECT id, name, updated_at, created_at"
					+ "FROM regd_goshuin_book_design_group");

			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				RegdGoshuinBookDesignGroup regdgoshuinBookDesignGroup = new RegdGoshuinBookDesignGroup();

				// 利用者インスタンスに検索結果をセット
				regdgoshuinBookDesignGroup.setId(resultSet.getInt("id"));
				regdgoshuinBookDesignGroup.setName(resultSet.getString("name"));
				regdgoshuinBookDesignGroup.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
				regdgoshuinBookDesignGroup.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

        		list.add(regdgoshuinBookDesignGroup);

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

	/*御朱印帳登録一覧デザイングループの登録*/

	/**
	 *
	 *
	 * @param
	 *
	 * @return
	 * @throws
	 */

	public Pair<Boolean, Integer> insert(RegdGoshuinBookDesignGroup regdGoshuinBookDesignGroup) throws Exception {

        // コネクションを取得
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        Pair<Boolean, Integer> pair;
        int designGroupId;

        try {
            // SQL文を準備（created_at / updated_at はDB側で現在時刻をセット）
            statement = connection.prepareStatement(
                "INSERT INTO regd_goshuin_book_design_group (name) " +
                "VALUES (?)"
            );

            // パラメータをバインド
            statement.setString(1, regdGoshuinBookDesignGroup.getName());

            // 実行（INSERTなので executeUpdate）
            count = statement.executeUpdate();

            statement.close();

            //登録したグループのidを取得
            statement = connection.prepareStatement(
            		"SELECT id FROM regd_goshuin_book_design_group"
            		+ "WHERE name = ?"
            		);
            // パラメータをバインド
            statement.setString(1, regdGoshuinBookDesignGroup.getName());

			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();
			// 検索結果をセット
			designGroupId = resultSet.getInt("id");


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
			pair = Pair.of(Boolean.TRUE, Integer.valueOf(designGroupId));
		} else {
			// 実行件数がそれ以外の場合
			pair = Pair.of(Boolean.FALSE, null);
		}

		return pair;
    }

}
