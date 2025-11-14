package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.RegdGoshuinBookDesign;

public class RegdGoshuinBookDesignDao extends Dao{

    // 登録済み御朱印帳デザイン一覧を取得():list<RegdGoshuinBookDesign>
    // 御朱印帳デザインをregdGoshuinBookDesignに登録
	// 御朱印帳デザインIDを指定して1件取得


    /**
     * 登録済み御朱印帳デザイン一覧を取得する
     * @return RegdGoshuinBookDesignのリスト
     * @throws Exception
     */
    public List<RegdGoshuinBookDesign> getAll() throws Exception {
        // リストを初期化
        List<RegdGoshuinBookDesign> designs = new ArrayList<>();
        // コネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "SELECT id, goshuin_book_design_group_id, name, image_path, updated_at, created_at " +
                "FROM regd_goshuin_book_design"
            );

            // プリペアードステートメントを実行する
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                RegdGoshuinBookDesign design = new RegdGoshuinBookDesign();
                design.setId(resultSet.getInt("id"));
                design.setGoshuinBookDesignGroupId(resultSet.getInt("goshuin_book_design_group_id"));
                design.setName(resultSet.getString("name"));
                design.setImagePath(resultSet.getString("image_path"));
                design.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                design.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

                designs.add(design);
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

        return designs;
    }




    /**
     * insertメソッド 御朱印帳デザインを登録する
     * @param
     *
     */
    public boolean insert(RegdGoshuinBookDesign design) throws Exception {
    	// コネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        int count = 0;

        try {
            statement = connection.prepareStatement(
                "INSERT INTO regd_goshuin_book_design " +
                "(id, goshuin_book_design_group_id, name, image_path) " +
                "VALUES (?, ?, ?, ?)"
            );

            statement.setInt(1, design.getId());
            statement.setInt(2, design.getGoshuinBookDesignGroupId());
            statement.setString(3, design.getName());
            statement.setString(4, design.getImagePath());

            count = statement.executeUpdate();
            return count > 0;
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }
    }



    /**
     * 御朱印帳デザインIDを指定して1件取得する
     * @param id デザインID
     * @return RegdGoshuinBookDesign インスタンス（存在しない場合は null）
     * @throws Exception
     */
    public RegdGoshuinBookDesign getById(int id) throws Exception {
        RegdGoshuinBookDesign design = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "SELECT id, goshuin_book_design_group_id, name, image_path, updated_at, created_at " +
                "FROM regd_goshuin_book_design WHERE id = ?"
            );
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                design = new RegdGoshuinBookDesign();
                design.setId(resultSet.getInt("id"));
                design.setGoshuinBookDesignGroupId(resultSet.getInt("goshuin_book_design_group_id"));
                design.setName(resultSet.getString("name"));
                design.setImagePath(resultSet.getString("image_path"));
                design.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
				design.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        return design;
    }

}
