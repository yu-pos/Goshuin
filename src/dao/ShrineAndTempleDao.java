package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

				//もし所持タグIDリストに検索タグIDが含まれていたら取得
				if (haveTagIdList.contains(shrineAndTempleTagIdList)) {
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

				//もし所持タグIDリストに検索タグIDが含まれていたら取得
				if (haveTagIdList.contains(shrineAndTempleTagIdList)) {
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


	public boolean insert(ShrineAndTemple shrineAndTemple) throws Exception {

		return false;
	}

	public boolean update(ShrineAndTemple shrineAndTemple) throws Exception {

		return false;
	}
}
