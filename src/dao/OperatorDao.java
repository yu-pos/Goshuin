package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import bean.Operator;
import util.PasswordUtil;

public class OperatorDao extends Dao {

    /* =========================
       単一取得
       ========================= */
    public Operator getById(int id) throws Exception {

        Operator operator = new Operator();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "SELECT id, password, is_admin, is_first_login_completed, is_enable, updated_at, created_at " +
                "FROM operator WHERE id = ?"
            );

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                operator.setId(resultSet.getInt("id"));
                operator.setPassword(resultSet.getString("password"));
                operator.setAdmin(resultSet.getBoolean("is_admin"));
                operator.setFirstLoginCompleted(resultSet.getBoolean("is_first_login_completed"));
                operator.setEnable(resultSet.getBoolean("is_enable"));

                // null対策（DB側でNULLになる可能性があるなら）
                if (resultSet.getTimestamp("updated_at") != null) {
                    operator.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                }
                if (resultSet.getTimestamp("created_at") != null) {
                    operator.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                }
            } else {
                operator = null;
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
            }
        }
        return operator;
    }

    /* =========================
       新規運営者アカウント登録
       ========================= */
    public Pair<Boolean, Integer> insert(String password, boolean isAdmin) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet keys = null;

        int count = 0;
        int operatorId = -1;

        try {
            statement = connection.prepareStatement(
                "INSERT INTO operator (password, is_admin) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            // ★ パスワードをハッシュ化して保存
            statement.setString(1, PasswordUtil.hash(password));
            statement.setBoolean(2, isAdmin);

            count = statement.executeUpdate();

            keys = statement.getGeneratedKeys();
            if (keys.next()) {
                operatorId = keys.getInt(1);
            } else {
                throw new Exception("運営者IDの取得に失敗しました");
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (keys != null) {
                try { keys.close(); } catch (SQLException sqle) { throw sqle; }
            }
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
            }
        }

        return Pair.of(count > 0, operatorId);
    }

    /* =========================
       ログイン認証（移行対応）
       - まずBCrypt照合
       - 失敗したら「平文equals」も許す（旧データ用）
       - 平文で通ったらその場でハッシュに更新
       ========================= */
    public Operator login(int id, String password) throws Exception {

        Operator operator = getById(id);
        if (operator == null) return null;

        String stored = operator.getPassword();

        // 1) BCryptならBCryptで照合
        if (PasswordUtil.isBCryptHash(stored)) {
            if (!PasswordUtil.matches(password, stored)) return null;
            return operator;
        }

        // 2) 旧データ（平文）救済：equals
        if (stored != null && stored.equals(password)) {
            // ★ 自動でハッシュに更新して移行完了にする
            operator.setPassword(password); // update側でhashする設計にしているので平文を入れる
            operator.setFirstLoginCompleted(operator.isFirstLoginCompleted()); // 既存値維持
            operator.setAdmin(operator.isAdmin());
            operator.setEnable(operator.isEnable());
            update(operator);
            // DB更新後、取り直して返す（安全）
            return getById(id);
        }

        return null;
    }

    /* =========================
       パスワード更新
       - operator.getPassword() は「新しい平文」が入っている前提
       ========================= */
    public boolean update(Operator operator) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            statement = connection.prepareStatement(
                "UPDATE operator SET password = ?, is_admin = ?, is_first_login_completed = ?, " +
                "is_enable = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?"
            );

            // ★ ここで必ずハッシュ化して保存
            statement.setString(1, PasswordUtil.hash(operator.getPassword()));
            statement.setBoolean(2, operator.isAdmin());
            statement.setBoolean(3, operator.isFirstLoginCompleted());
            statement.setBoolean(4, operator.isEnable());
            statement.setInt(5, operator.getId());

            count = statement.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
            }
        }

        return count > 0;
    }

    /* =========================
       全運営者一覧取得
       ========================= */
    public List<Operator> getAll(Operator Operator) throws Exception {

        List<Operator> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM operator ORDER BY id ASC");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Operator operator = new Operator();
                operator.setId(resultSet.getInt("id"));
                operator.setPassword(resultSet.getString("password"));
                operator.setAdmin(resultSet.getBoolean("is_admin"));
                operator.setFirstLoginCompleted(resultSet.getBoolean("is_first_login_completed"));
                operator.setEnable(resultSet.getBoolean("is_enable"));

                if (resultSet.getTimestamp("updated_at") != null) {
                    operator.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                }
                if (resultSet.getTimestamp("created_at") != null) {
                    operator.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                }

                list.add(operator);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
            }
        }

        return list;
    }
}
