package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import bean.OwnedGoshuinBookSticker;
import bean.RegdGoshuinBookSticker;

public class OwnedGoshuinBookStickerDao extends Dao {

	/* ===================== 登録（INSERT） ===================== */
	/** 所持御朱印帳ステッカー情報を登録 */
	public boolean insert(OwnedGoshuinBookSticker entity) throws Exception {
		if (entity == null || entity.getGoshuinBookSticker() == null) return false;

		Connection con = null;
		PreparedStatement ps = null;
		int count = 0;

		try {
			con = getConnection();
			String sql = "INSERT INTO owned_goshuin_book_sticker "
					+ "(user_id, sticker_id, updated_at, created_at) VALUES (?, ?, ?, ?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, entity.getUserId());
			ps.setInt(2, entity.getGoshuinBookSticker().getId());
			LocalDateTime now = LocalDateTime.now();
			ps.setTimestamp(3, toTs(now));
			ps.setTimestamp(4, toTs(now));
			count = ps.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			if (ps != null) try { ps.close(); } catch (SQLException ex) { throw ex; }
			if (con != null) try { con.close(); } catch (SQLException ex) { throw ex; }
		}
		return count > 0;
	}

	/* ===================== 単一取得 ===================== */
	public OwnedGoshuinBookSticker get(int userId, int stickerId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			String sql = "SELECT * FROM owned_goshuin_book_sticker WHERE user_id = ? AND sticker_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, stickerId);
			rs = ps.executeQuery();
			if (rs.next()) {
				return map(rs);
			}
			return null;
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ex) { throw ex; }
			if (ps != null) try { ps.close(); } catch (SQLException ex) { throw ex; }
			if (con != null) try { con.close(); } catch (SQLException ex) { throw ex; }
		}
	}

	/* ===================== ユーザ別一覧 ===================== */
	public List<OwnedGoshuinBookSticker> findByUser(int userId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<OwnedGoshuinBookSticker> list = new ArrayList<>();
		try {
			con = getConnection();
			String sql = "SELECT * FROM owned_goshuin_book_sticker WHERE user_id = ? ORDER BY sticker_id ASC";
			ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(map(rs));
			}
			return list;
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ex) { throw ex; }
			if (ps != null) try { ps.close(); } catch (SQLException ex) { throw ex; }
			if (con != null) try { con.close(); } catch (SQLException ex) { throw ex; }
		}
	}

	/* ===================== 存在確認 ===================== */
	public boolean exists(int userId, int stickerId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			String sql = "SELECT 1 FROM owned_goshuin_book_sticker WHERE user_id = ? AND sticker_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, stickerId);
			rs = ps.executeQuery();
			return rs.next();
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ex) { throw ex; }
			if (ps != null) try { ps.close(); } catch (SQLException ex) { throw ex; }
			if (con != null) try { con.close(); } catch (SQLException ex) { throw ex; }
		}
	}

	/* ===================== 削除 ===================== */
	public boolean delete(int userId, int stickerId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		int count = 0;

		try {
			con = getConnection();
			String sql = "DELETE FROM owned_goshuin_book_sticker WHERE user_id = ? AND sticker_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, stickerId);
			count = ps.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			if (ps != null) try { ps.close(); } catch (SQLException ex) { throw ex; }
			if (con != null) try { con.close(); } catch (SQLException ex) { throw ex; }
		}
		return count > 0;
	}

	/* ===================== マッピング ===================== */
	private OwnedGoshuinBookSticker map(ResultSet rs) throws Exception {
		OwnedGoshuinBookSticker o = new OwnedGoshuinBookSticker();

		int userId    = rs.getInt("user_id");
		int stickerId = rs.getInt("sticker_id");
		o.setUserId(userId);
		o.setUpdatedAt(fromTs(rs.getTimestamp("updated_at")));
		o.setCreatedAt(fromTs(rs.getTimestamp("created_at")));

		// マスタはIDだけ詰める（必要なら RegdGoshuinBookStickerDao#get(stickerId) で取得に差し替え）
		RegdGoshuinBookSticker master = new RegdGoshuinBookSticker();
		master.setId(stickerId);
		o.setGoshuinBookSticker(master);

		return o;
	}

	/* ===================== TS 変換 ===================== */
	private static Timestamp toTs(LocalDateTime t) {
		return (t == null) ? null : Timestamp.valueOf(t);
	}
	private static LocalDateTime fromTs(Timestamp ts) {
		return (ts == null) ? null : ts.toLocalDateTime();
	}
}
