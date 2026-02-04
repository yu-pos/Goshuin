package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.apache.commons.lang3.tuple.Pair;

import bean.User;
import util.PasswordUtil;

public class UserDao extends Dao {

    public User getById(int id) throws Exception {

        User user = new User();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "SELECT id, user_name, real_name, birth_date, address, tel_number, password, " +
                "active_goshuin_book_id, point, user_rank, goshuin_count, profile_image_path, " +
                "my_goshuin_book_id, is_my_goshuin_book_public, last_login_at, last_omikuji_at, updated_at, created_at " +
                "FROM user WHERE id = ?"
            );

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            GoshuinBookDao goshuinBookDao = new GoshuinBookDao();

            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("user_name"));
                user.setRealName(resultSet.getString("real_name"));

                if (resultSet.getTimestamp("birth_date") != null) {
                    user.setBirthDate(resultSet.getTimestamp("birth_date").toLocalDateTime());
                }

                user.setAddress(resultSet.getString("address"));
                user.setTelNumber(resultSet.getString("tel_number"));
                user.setPassword(resultSet.getString("password"));

                int activeId = resultSet.getInt("active_goshuin_book_id");
                if (!resultSet.wasNull() && activeId != 0) {
                    user.setActiveGoshuinBook(goshuinBookDao.getById(activeId));
                }

                user.setPoint(resultSet.getInt("point"));
                user.setRank(resultSet.getInt("user_rank"));
                user.setGoshuinCount(resultSet.getInt("goshuin_count"));
                user.setProfileImagePath(resultSet.getString("profile_image_path"));

                int myBookId = resultSet.getInt("my_goshuin_book_id");
                if (!resultSet.wasNull() && myBookId != 0) {
                    user.setMyGoshuinBook(goshuinBookDao.getById(myBookId));
                }

                user.setMyGoshuinBookPublic(resultSet.getBoolean("is_my_goshuin_book_public"));

                if (resultSet.getTimestamp("last_login_at") != null) {
                    user.setLastLoginAt(resultSet.getTimestamp("last_login_at").toLocalDateTime());
                }

                if (resultSet.getTimestamp("last_omikuji_at") != null) {
                    user.setLastOmikujiAt(resultSet.getTimestamp("last_omikuji_at").toLocalDateTime());
                }

                if (resultSet.getTimestamp("updated_at") != null) {
                    user.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                }
                if (resultSet.getTimestamp("created_at") != null) {
                    user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                }

            } else {
                user = null;
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

        return user;
    }

    public User getByTel(String telNumber) throws Exception {
        User user = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "SELECT id, user_name, real_name, birth_date, address, tel_number, password, " +
                "       active_goshuin_book_id, point, user_rank, goshuin_count, profile_image_path, " +
                "       my_goshuin_book_id, is_my_goshuin_book_public, " +
                "       last_login_at, last_omikuji_at, updated_at, created_at " +
                "FROM user WHERE tel_number = ?"
            );
            statement.setString(1, telNumber);

            ResultSet rs = statement.executeQuery();
            GoshuinBookDao goshuinBookDao = new GoshuinBookDao();

            if (rs.next()) {
                user = new User();

                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("user_name"));
                user.setRealName(rs.getString("real_name"));

                Timestamp birthTs = rs.getTimestamp("birth_date");
                if (birthTs != null) user.setBirthDate(birthTs.toLocalDateTime());

                user.setAddress(rs.getString("address"));
                user.setTelNumber(rs.getString("tel_number"));
                user.setPassword(rs.getString("password"));

                int activeId = rs.getInt("active_goshuin_book_id");
                if (!rs.wasNull() && activeId != 0) {
                    user.setActiveGoshuinBook(goshuinBookDao.getById(activeId));
                }

                user.setPoint(rs.getInt("point"));
                user.setRank(rs.getInt("user_rank"));
                user.setGoshuinCount(rs.getInt("goshuin_count"));
                user.setProfileImagePath(rs.getString("profile_image_path"));

                int myBookId = rs.getInt("my_goshuin_book_id");
                if (!rs.wasNull() && myBookId != 0) {
                    user.setMyGoshuinBook(goshuinBookDao.getById(myBookId));
                }
                user.setMyGoshuinBookPublic(rs.getBoolean("is_my_goshuin_book_public"));

                Timestamp lastLoginTs = rs.getTimestamp("last_login_at");
                if (lastLoginTs != null) user.setLastLoginAt(lastLoginTs.toLocalDateTime());

                Timestamp lastOmikujiTs = rs.getTimestamp("last_omikuji_at");
                if (lastOmikujiTs != null) user.setLastOmikujiAt(lastOmikujiTs.toLocalDateTime());

                Timestamp updatedTs = rs.getTimestamp("updated_at");
                if (updatedTs != null) user.setUpdatedAt(updatedTs.toLocalDateTime());

                Timestamp createdTs = rs.getTimestamp("created_at");
                if (createdTs != null) user.setCreatedAt(createdTs.toLocalDateTime());

            } else {
                user = null;
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

        return user;
    }

    // ★ 旧平文→BCryptへ移行するための内部ヘルパー（平文ログイン成功時に呼ぶ）
    private boolean updatePasswordHash(int userId, String newHashedPassword) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "UPDATE user SET password = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?"
            );
            statement.setString(1, newHashedPassword);
            statement.setInt(2, userId);

            return statement.executeUpdate() == 1;

        } finally {
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
            }
        }
    }

    public boolean insert(User user) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet keys = null;

        int count = 0;

        try {
            statement = connection.prepareStatement(
                "INSERT INTO user(" +
                "  user_name, real_name, birth_date, address, tel_number, password, " +
                "  point, user_rank, goshuin_count, profile_image_path, " +
                "  active_goshuin_book_id, my_goshuin_book_id, " +
                "  is_my_goshuin_book_public, last_login_at, updated_at, created_at" +
                ") VALUES(" +
                "  ?, ?, ?, ?, ?, ?, " +
                "  0, 6, 0, NULL, " +
                "  NULL, NULL, " +
                "  FALSE, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP" +
                ")",
                Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getRealName());
            statement.setTimestamp(3, Timestamp.valueOf(user.getBirthDate()));
            statement.setString(4, user.getAddress());
            statement.setString(5, user.getTelNumber());

            // ★ パスワードをハッシュ化して保存
            statement.setString(6, PasswordUtil.hash(user.getPassword()));

            count = statement.executeUpdate();
            if (count != 1) return false;

            keys = statement.getGeneratedKeys();
            int userId;
            if (keys.next()) {
                userId = keys.getInt(1);
                user.setId(userId);
            } else {
                throw new Exception("ユーザーIDの取得に失敗しました");
            }

            GoshuinBookDao goshuinBookDao = new GoshuinBookDao();
            Pair<Boolean, Integer> pair = goshuinBookDao.insert(userId);
            if (!pair.getLeft()) {
                throw new Exception("御朱印帳の発行に失敗しました");
            }
            int goshuinBookId = pair.getRight();

            statement.close();
            statement = connection.prepareStatement(
                "UPDATE user SET active_goshuin_book_id = ?, my_goshuin_book_id = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?"
            );
            statement.setInt(1, goshuinBookId);
            statement.setInt(2, goshuinBookId);
            statement.setInt(3, userId);

            int count2 = statement.executeUpdate();
            if (count2 != 1) {
                throw new Exception("ユーザーの御朱印帳情報の更新に失敗しました");
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

        return true;
    }

    public boolean update(User user) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        int count = 0;

        try {

            statement = connection.prepareStatement(
                "UPDATE user SET user_name = ?, active_goshuin_book_id = ?, user_rank = ?, goshuin_count = ?, " +
                "profile_image_path = ?, my_goshuin_book_id = ?, is_my_goshuin_book_public = ?, " +
                "last_login_at = ?, last_omikuji_at = ?, point = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?"
            );

            statement.setString(1, user.getUserName());
            statement.setInt(2, user.getActiveGoshuinBook().getId());
            statement.setInt(3, user.getRank());
            statement.setInt(4, user.getGoshuinCount());

            String profileImagePath = user.getProfileImagePath();
            if (profileImagePath != null && profileImagePath.equals("default.png")) {
                statement.setString(5, null);
            } else {
                statement.setString(5, user.getProfileImagePath());
            }

            statement.setInt(6, user.getMyGoshuinBook().getId());
            statement.setBoolean(7, user.isMyGoshuinBookPublic());

            if (user.getLastLoginAt() != null) {
                statement.setTimestamp(8, Timestamp.valueOf(user.getLastLoginAt()));
            } else {
                statement.setTimestamp(8, null);

            }

            if (user.getLastOmikujiAt() != null) {
                statement.setTimestamp(9, Timestamp.valueOf(user.getLastOmikujiAt()));
            } else {
                statement.setTimestamp(9, null);
            }

            statement.setInt(10, user.getPoint());
            statement.setInt(11, user.getId());

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

        return count == 1;
    }

    /* =========================
       ログイン（移行対応）
       - BCryptならcheckpw
       - 平文ならequalsで許可 → その場でBCryptへ更新
       ========================= */
    public User login(String telNumber, String password) throws Exception {

        User user = getByTel(telNumber);
        if (user == null) return null;

        String stored = user.getPassword();

        // 1) BCryptならBCryptで照合
        if (PasswordUtil.isBCryptHash(stored)) {
            if (!PasswordUtil.matches(password, stored)) return null;
            return user;
        }

        // 2) 旧データ（平文）救済
        if (stored != null && stored.equals(password)) {
            // ★ 自動でハッシュへ移行
            String newHash = PasswordUtil.hash(password);
            updatePasswordHash(user.getId(), newHash);

            // 取り直して返す（パスワードがハッシュになって返ってくる）
            return getById(user.getId());
        }

        return null;
    }
}
