package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.RegdGoshuin;
import bean.ShrineAndTemple;


public class RegdGoshuinDao extends Dao {

	//読み取ったQRコードに記載されている神社仏閣IDから御朱印を検索():regdGoshuin
	//御朱印情報を登録():boolean

    /**
     * 神社仏閣IDから御朱印情報を検索するメソッド
     * @param shrineAndTempleId 神社仏閣ID（外部キー）
     * @return RegdGoshuin 該当する御朱印情報（見つからない場合は null）
     */
    public RegdGoshuin searchByShrineAndTempleId(int shrineAndTempleId) throws Exception{

    	// 登録御朱印インスタンスを初期化
    	RegdGoshuin regdgoshuin = new RegdGoshuin();
    	// コネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;

        try {
            // プリペアードステートメントにSQL文をセット（神社仏閣IDで検索）
            statement = connection.prepareStatement(
                "SELECT id, shrine_and_temple_id, sale_start_date, sale_end_date, image_path, updated_at, created_at "
                +"FROM regd_goshuin WHERE shrine_and_temple_id = ?"
            );

            // プリペアードステートメントに神社仏閣IDをバインド
            statement.setInt(1, shrineAndTempleId);
            // プリペアードステートメントを実行
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
            	// リザルトセットが存在する場合
                // 登録御朱印インスタンスに検索結果をセット
                regdgoshuin.setId(resultSet.getInt("id"));

                ShrineAndTemple sat = new ShrineAndTemple();
                sat.setId(resultSet.getInt("shrine_temple_id"));
                regdgoshuin.setShrineAndTemple(sat);

                regdgoshuin.setSaleStartDate(resultSet.getString("sale_start_date"));
                regdgoshuin.setSaleEndDate(resultSet.getString("sale_end_date"));
                regdgoshuin.setImagePath(resultSet.getString("image_path"));
                regdgoshuin.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                regdgoshuin.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            } else {
                // レコードが存在しない場合は null を返す
            	regdgoshuin = null;
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

        return regdgoshuin;

    }

    /**
     * 御朱印情報を保存するメソッド
     * @param regdgoshuin 保存対象の RegdGoshuin オブジェクト
     * @return boolean 保存成功なら true、失敗なら false
     */
    public boolean save(RegdGoshuin regdoshuin) throws Exception{
    	//コネクションを確立
    	Connection connection = getConnection();
    	//プリペアードステートメント
    	PreparedStatement statement = null;
    	//実行件数
    	int count = 0;

    	try {


    	}
    }
}
