package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.RegdGoshuin;


public class RegdGoshuinDao extends Dao {

	//読み取ったQRコードに記載されている神社仏閣IDから御朱印を検索():regdGoshuin
	//御朱印情報を登録():boolean

    /**
     * 神社仏閣IDから御朱印情報を検索するメソッド
     *
     * @param shrineAndTempleId 神社仏閣ID（外部キー）
     * @return List<RegdGoshuin> 該当する御朱印情報（見つからない場合は null）
     * @throws Exception
     */
    public List<RegdGoshuin> searchByShrineAndTemple(int shrineAndTempleId) throws Exception{


    	// コネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;

        List<RegdGoshuin> list = new ArrayList<>();

        try {
            // プリペアードステートメントにSQL文をセット（神社仏閣IDで検索）
            statement = connection.prepareStatement(
                "SELECT id, shrine_and_temple_id, description, sale_start_date, sale_end_date, image_path, updated_at, created_at "
                + " FROM regd_goshuin WHERE shrine_and_temple_id = ?"
            );

            // プリペアードステートメントに神社仏閣IDをバインド
            statement.setInt(1, shrineAndTempleId);
            // プリペアードステートメントを実行
            ResultSet resultSet = statement.executeQuery();

            ShrineAndTempleDao shrineAndTempleDao = new ShrineAndTempleDao();

            while(resultSet.next()) {

            	// 登録御朱印インスタンスを初期化
            	RegdGoshuin regdGoshuin = new RegdGoshuin();

                // 登録御朱印インスタンスに検索結果をセット
            	regdGoshuin.setId(resultSet.getInt("id"));

                regdGoshuin.setShrineAndTemple(shrineAndTempleDao.getById(resultSet.getInt("shrine_and_temple_id")));
                regdGoshuin.setDescription(resultSet.getString("description"));
                regdGoshuin.setSaleStartDate(resultSet.getString("sale_start_date"));
                regdGoshuin.setSaleEndDate(resultSet.getString("sale_end_date"));
                regdGoshuin.setImagePath(resultSet.getString("image_path"));
                regdGoshuin.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                regdGoshuin.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

                list.add(regdGoshuin);
            }
        } catch (Exception e) {
            throw e; // 呼び出し元に例外を投げる
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
     * 御朱印情報を登録(保存)するメソッド
     *
     * @param regdgoshuin 保存対象の RegdGoshuin オブジェクト
     * @return boolean 保存成功なら true、失敗なら false
     */
    public boolean insert(RegdGoshuin regdgoshuin) throws Exception {
        // コネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        // 実行件数
        int count = 0;

        try {
            // SQL文を準備
            String sql = "INSERT INTO regd_goshuin " +
                         "(shrine_and_temple_id, description, sale_start_date, sale_end_date, image_path) " +
                         "VALUES (?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(sql);

            // プレースホルダに値をバインド
            statement.setInt(1, regdgoshuin.getShrineAndTemple().getId());
            statement.setString(2, regdgoshuin.getDescription());
            statement.setString(3, regdgoshuin.getSaleStartDate());
            statement.setString(4, regdgoshuin.getSaleEndDate());
            statement.setString(5, regdgoshuin.getImagePath());

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
        // 1件以上登録できれば true
        return count > 0;
    }


    /**
     * getByIdメソッド 御朱印IDを指定して、登録御朱印インスタンスを一件取得する
     *
     * @param id:int
     * @return 登録御朱印クラスのインスタンス 存在しない場合はnull
     * @throws Exception
     */
    public RegdGoshuin getById(int id) throws Exception{
    	// 登録御朱印インスタンスを初期化
    	RegdGoshuin rg = new RegdGoshuin();
    	// コネクションを確立
    	Connection connection = getConnection();
    	// プリペアードステートメント
    	PreparedStatement statement = null;

    	try {
    		// プリペアードステートメントにSQL文をセット
    		statement = connection.prepareStatement("SELECT id, shrine_and_temple_id, description, sale_start_date, sale_end_date, image_path, updated_at, created_at"
    				+ " FROM regd_goshuin where id = ?");
    		//プリペアードステートメントに御朱印IDをバインド
    		statement.setInt(1, id);
    		// プリペアードステートメントを実行
    		ResultSet resultSet = statement.executeQuery();

    		ShrineAndTempleDao shrineAndTempleDao = new ShrineAndTempleDao();

    		if (resultSet.next()) {
    			// リザルトセットが存在する場合
    			rg.setId(resultSet.getInt("id"));

                rg.setShrineAndTemple(shrineAndTempleDao.getById(resultSet.getInt("shrine_and_temple_id")));
                rg.setDescription(resultSet.getString("description"));
    			rg.setSaleStartDate(resultSet.getString("sale_start_date"));
                rg.setSaleEndDate(resultSet.getString("sale_end_date"));
                rg.setImagePath(resultSet.getString("image_path"));
                rg.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                rg.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

    		}
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
    	return rg; // 見つからなければ null、見つかれば RegdGoshuin インスタンス
    }

}
