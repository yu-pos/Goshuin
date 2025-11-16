package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.RegdGoshuinBookSticker;

public class RegdGoshuinBookStickerDao extends Dao{

	/* IDでステッカー情報を取得(id : int) : RegdGoshuinBookSticker
	 * 登録済み御朱印帳ステッカー一覧を取得():list<RegdGoshuinBookSticker>
	 * 御朱印超ステッカー情報を登録() : boolean
	 */

	/**
	 * getByIdメソッド ステッカーIDを指定して登録ステッカーインスタンスを1件取得する
	 *
	 * @param id:int
	 *            ステッカーID
	 * @return 登録ステッカークラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public RegdGoshuinBookSticker getById(int id) throws Exception {
		// 利用者インスタンスを初期化
		RegdGoshuinBookSticker regdGoshuinBookSticker = new RegdGoshuinBookSticker ();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT id, name, image_path, updated_at, created_at"
					+ " FROM regd_goshuin_book_sticker WHERE id = ?");
			// プリペアードステートメントにステッカーIDをバインド
			statement.setInt(1, id);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// 利用者インスタンスに検索結果をセット
				regdGoshuinBookSticker.setId(resultSet.getInt("id"));
				regdGoshuinBookSticker.setName(resultSet.getString("name"));
				regdGoshuinBookSticker.setImagePath(resultSet.getString("image_path"));
				regdGoshuinBookSticker.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
				regdGoshuinBookSticker.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

			} else {
				// リザルトセットが存在しない場合
				// 利用者インスタンスにnullをセット
				regdGoshuinBookSticker = null;
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

		return regdGoshuinBookSticker;
	}


	/**
	 * getAllメソッド 全ての登録ステッカー一覧を取得する
	 *
	 * @return 全登録ステッカーのリスト
	 * @throws Exception
	 */
	public List<RegdGoshuinBookSticker> getAll() throws Exception {


		// リストを初期化
		List<RegdGoshuinBookSticker> list = new ArrayList<>();
		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("SELECT id, name, image_path, updated_at, created_at"
					+ "FROM regd_goshuin_book_sticker");

			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				RegdGoshuinBookSticker regdGoshuinBookSticker = new RegdGoshuinBookSticker();

				// 利用者インスタンスに検索結果をセット
				regdGoshuinBookSticker.setId(resultSet.getInt("id"));
				regdGoshuinBookSticker.setName(resultSet.getString("name"));
				regdGoshuinBookSticker.setImagePath(resultSet.getString("image_path"));
				regdGoshuinBookSticker.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
				regdGoshuinBookSticker.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

        		list.add(regdGoshuinBookSticker);

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
	 * insertメソッド ステッカー情報を登録
	 *
	 * @param regdGoshuinBookSticker:RegdGoshuinBookSticker
	 *            登録ステッカー情報
	 * @return 成功可否
	 * @throws Exception
	 */
    public boolean insert(RegdGoshuinBookSticker regdGoshuinBookSticker) throws Exception {

        // コネクションを取得
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            // SQL文を準備（created_at / updated_at はDB側で現在時刻をセット）
            statement = connection.prepareStatement(
                "INSERT INTO regd_goshuin_book_sticker (name, image_path) " +
                "VALUES (?, ?)"
            );

            // パラメータをバインド
            statement.setString(1, regdGoshuinBookSticker.getName());
            statement.setString(2, regdGoshuinBookSticker.getImagePath());

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
