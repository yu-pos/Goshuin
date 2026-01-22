package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import bean.ShrineAndTemple;
import bean.ShrineAndTempleTag;

public class ShrineAndTempleDao extends Dao{



	//対象の神社仏閣情報を取得():ShrineAndTemple
	//選択されたタグを検索し神社仏閣情報一覧を取得():list<ShrineAndTemple>
	//入力された語句で神社仏閣を検索し、一覧を取得():list<ShrineAndTemple>
	//入力されたタグと名称で神社仏閣一覧を検索し、一覧を取得():list<ShrineAndTemple>
	//神社仏閣情報を登録():boolean
	//神社仏閣情報を変更():boolean

	/**
	 * getByIdメソッド 神社仏閣IDを指定して神社仏閣インスタンスを1件取得する
	 *
	 * @param id:int
	 *            神社仏閣ID
	 * @return 神社仏閣クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public ShrineAndTemple getById(int id) throws Exception {
		//shrineAndTempleDao.getById(取得したい神社仏閣のID)
		// 利用者インスタンスを初期化
		ShrineAndTemple shrineAndTemple = new ShrineAndTemple();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT * FROM shrine_and_temple WHERE id=?");
			// プリペアードステートメントに神社仏閣IDをバインド
			statement.setInt(1, id);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();


			//神社仏閣タグDaoを宣言
			ShrineAndTempleTagDao shrineAndTempleTagDao = new ShrineAndTempleTagDao();

			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// 神社仏閣インスタンスに検索結果をセット
				shrineAndTemple.setId(resultSet.getInt("id"));
				shrineAndTemple.setName(resultSet.getString("name"));
				shrineAndTemple.setAddress(resultSet.getString("Address"));
				shrineAndTemple.setDescription(resultSet.getString("Description"));
				shrineAndTemple.setAreaInfo(resultSet.getString("Area_info"));
				shrineAndTemple.setMapLink(resultSet.getString("map_link"));
				shrineAndTemple.setImagePath(resultSet.getString("image_path"));
				shrineAndTemple.setTagList(shrineAndTempleTagDao.searchByShrineAndTemple(resultSet.getInt("id")));

				shrineAndTemple.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
				shrineAndTemple.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
			} else {
				// リザルトセットが存在しない場合
				// 神社仏閣インスタンスにnullを

				shrineAndTemple = null;
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

		return shrineAndTemple;
	}

	public List<ShrineAndTemple> searchByTag(List<Integer> shrineAndTempleTagIdList) throws Exception {


		// リストを初期化
		List<ShrineAndTemple> list = new ArrayList<>();
		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		//神社仏閣タグDaoを宣言
		ShrineAndTempleTagDao shrineAndTempleTagDao = new ShrineAndTempleTagDao();

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT * FROM shrine_and_temple");


			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				//対象の行の神社仏閣が持っているタグ一覧を取得
				List<ShrineAndTempleTag> haveTagList = shrineAndTempleTagDao.searchByShrineAndTemple(resultSet.getInt("id"));

				//所持タグのIdリストを作成
				List<Integer> haveTagIdList = new ArrayList<>();
				for (ShrineAndTempleTag shrineAndTempleTag : haveTagList) {
					haveTagIdList.add(shrineAndTempleTag.getId());
				}

				if (haveTagIdList.containsAll(shrineAndTempleTagIdList)) {
					ShrineAndTemple shrineAndTemple = new ShrineAndTemple();


					// 神社仏閣インスタンスに検索結果をセット
					shrineAndTemple.setId(resultSet.getInt("id"));
					shrineAndTemple.setName(resultSet.getString("name"));
					shrineAndTemple.setAddress(resultSet.getString("Address"));
					shrineAndTemple.setDescription(resultSet.getString("Description"));
					shrineAndTemple.setAreaInfo(resultSet.getString("Area_info"));
					shrineAndTemple.setMapLink(resultSet.getString("map_link"));
					shrineAndTemple.setImagePath(resultSet.getString("image_path"));
					shrineAndTemple.setTagList(haveTagList);

					shrineAndTemple.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
					shrineAndTemple.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());


	        		list.add(shrineAndTemple);
				}


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

	public List<ShrineAndTemple> searchByName(String name) throws Exception {


		// リストを初期化
		List<ShrineAndTemple> list = new ArrayList<>();
		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		//神社仏閣タグDaoを宣言
		ShrineAndTempleTagDao shrineAndTempleTagDao = new ShrineAndTempleTagDao();

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT * FROM shrine_and_temple WHERE name LIKE ?");
			statement.setString(1, "%" + name + "%");

			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				//対象の行の神社仏閣が持っているタグ一覧を取得
				List<ShrineAndTempleTag> haveTagList = shrineAndTempleTagDao.searchByShrineAndTemple(resultSet.getInt("id"));

				ShrineAndTemple shrineAndTemple = new ShrineAndTemple();

				// 神社仏閣インスタンスに検索結果をセット
				shrineAndTemple.setId(resultSet.getInt("id"));
				shrineAndTemple.setName(resultSet.getString("name"));
				shrineAndTemple.setAddress(resultSet.getString("Address"));
				shrineAndTemple.setDescription(resultSet.getString("Description"));
				shrineAndTemple.setAreaInfo(resultSet.getString("Area_info"));
				shrineAndTemple.setMapLink(resultSet.getString("map_link"));
				shrineAndTemple.setImagePath(resultSet.getString("image_path"));
				shrineAndTemple.setTagList(haveTagList);

				shrineAndTemple.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
				shrineAndTemple.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

        		list.add(shrineAndTemple);



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

	public List<ShrineAndTemple> searchByNameAndTag(String name, List<Integer> shrineAndTempleTagIdList) throws Exception {

		// リストを初期化
		List<ShrineAndTemple> list = new ArrayList<>();
		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		//神社仏閣タグDaoを宣言
		ShrineAndTempleTagDao shrineAndTempleTagDao = new ShrineAndTempleTagDao();

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT * FROM shrine_and_temple WHERE name LIKE ?");
			statement.setString(1, "%" + name + "%");

			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				//対象の行の神社仏閣が持っているタグ一覧を取得
				List<ShrineAndTempleTag> haveTagList = shrineAndTempleTagDao.searchByShrineAndTemple(resultSet.getInt("id"));

				//所持タグのIdリストを作成
				List<Integer> haveTagIdList = new ArrayList<>();
				for (ShrineAndTempleTag shrineAndTempleTag : haveTagList) {
					haveTagIdList.add(shrineAndTempleTag.getId());
				}


				if (haveTagIdList.containsAll(shrineAndTempleTagIdList)) {
					ShrineAndTemple shrineAndTemple = new ShrineAndTemple();

					// 神社仏閣インスタンスに検索結果をセット
					shrineAndTemple.setId(resultSet.getInt("id"));
					shrineAndTemple.setName(resultSet.getString("name"));
					shrineAndTemple.setAddress(resultSet.getString("Address"));
					shrineAndTemple.setDescription(resultSet.getString("Description"));
					shrineAndTemple.setAreaInfo(resultSet.getString("Area_info"));
					shrineAndTemple.setMapLink(resultSet.getString("map_link"));
					shrineAndTemple.setImagePath(resultSet.getString("image_path"));
					shrineAndTemple.setTagList(haveTagList);

					shrineAndTemple.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
					shrineAndTemple.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

	        		list.add(shrineAndTemple);
				}


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


	public Pair<Boolean, Integer> insert(ShrineAndTemple shrineAndTemple) throws Exception {

		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		ResultSet keys = null;
		int shrineAndTempleId = -1;

		// 実行件数
		int count = 0;

		try {

			// 利用者が存在した場合、情報を更新
			// プリペアードステートメントにUPDATE文をセット
			statement = connection.prepareStatement("INSERT INTO shrine_and_temple(name, address, description, area_info, map_link, image_path) "
					+ " VALUES(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			// プリペアードステートメントに値をバインド
			statement.setString(1, shrineAndTemple.getName());
			statement.setString(2, shrineAndTemple.getAddress());
			statement.setString(3, shrineAndTemple.getDescription());
			statement.setString(4, shrineAndTemple.getAreaInfo());
			statement.setString(5, shrineAndTemple.getMapLink());
			statement.setString(6, shrineAndTemple.getImagePath());

			// プリペアードステートメントを実行
			count = statement.executeUpdate();

			// ② 発番された user.id を取得
	        keys = statement.getGeneratedKeys();

	        if (keys.next()) {
	            shrineAndTempleId = keys.getInt(1);
	            shrineAndTemple.setId(shrineAndTempleId);
	        } else {
	            throw new Exception("神社仏閣IDの取得に失敗しました");
	        }

			statement.close();

			//タグ情報を登録
			for (ShrineAndTempleTag tag : shrineAndTemple.getTagList()) {

				statement = connection.prepareStatement("INSERT INTO shrine_and_temple_tagging(tag_id, shrine_and_temple_id)"
						+ " VALUES(?, ?)");
				statement.setInt(1, tag.getId());
				statement.setInt(2, shrineAndTemple.getId());
				statement.executeUpdate();
				statement.close();
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

		if (count == 1) {
			// 実行件数1件の場合
			return Pair.of(true, shrineAndTempleId);
		} else {
			// 実行件数がそれ以外の場合
			return Pair.of(false, -1);
		}
	}

	public boolean update(ShrineAndTemple shrineAndTemple) throws Exception {

		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		// 実行件数
		int count = 0;

		try {

			// 利用者が存在した場合、情報を更新
			// プリペアードステートメントにUPDATE文をセット
			statement = connection.prepareStatement("UPDATE shrine_and_temple SET"
					+ " name = ?, address = ?, description = ?, area_info = ?, map_link = ?, image_path = ?, updated_at = CURRENT_TIMESTAMP"
					+ " WHERE id = ?");
			// プリペアードステートメントに値をバインド
			statement.setString(1, shrineAndTemple.getName());
			statement.setString(2, shrineAndTemple.getAddress());
			statement.setString(3, shrineAndTemple.getDescription());
			statement.setString(4, shrineAndTemple.getAreaInfo());
			statement.setString(5, shrineAndTemple.getMapLink());
			statement.setString(6, shrineAndTemple.getImagePath());
			statement.setInt(7, shrineAndTemple.getId());

			// プリペアードステートメントを実行
			count = statement.executeUpdate();

			statement.close();

			//タグ情報を更新
			//タグ情報を一度削除
			statement = connection.prepareStatement("DELETE FROM shrine_and_temple_tagging "
					+ " WHERE shrine_and_temple_id = ?");

			statement.setInt(1, shrineAndTemple.getId());
			statement.executeUpdate();
			statement.close();

			//タグ情報を登録
			for (ShrineAndTempleTag tag : shrineAndTemple.getTagList()) {

				statement = connection.prepareStatement("INSERT INTO shrine_and_temple_tagging(tag_id, shrine_and_temple_id)"
						+ " VALUES(?, ?)");
				statement.setInt(1, tag.getId());
				statement.setInt(2, shrineAndTemple.getId());
				statement.executeUpdate();
				statement.close();
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

		if (count == 1) {
			// 実行件数1件の場合
			return true;
		} else {
			// 実行件数がそれ以外の場合
			return false;
		}
	}
}
