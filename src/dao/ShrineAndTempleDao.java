package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import bean.ShrineAndTemple;
import bean.ShrineAndTempleTag;

public class ShrineAndTempleDao {



	//対象の神社仏閣情報を取得():ShrineAndTemple
	//選択されたタグを検索し神社仏閣情報一覧を取得():list<ShrineAndTemple>
	//入力された語句で神社仏閣を検索し、一覧を取得():list<ShrineAndTemple>
	//入力されたタグと名称で神社仏閣一覧を検索し、一覧を取得():list<ShrineAndTemple>
	//神社仏閣情報を登録():boolean
	/**
	 * getByIdメソッド 利用者IDを指定して利用者インスタンスを1件取得する
	 *
	 * @param id:int
	 *            利用者ID
	 * @return 利用者クラスのインスタンス 存在しない場合はnull
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


			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// 利用者インスタンスに検索結果をセット
				shrineAndTemple.setId(resultSet.getInt("id"));
				shrineAndTemple.setName(resultSet.getString("name"));
				shrineAndTemple.setAddress(resultSet.getString("Address"));
				shrineAndTemple.setDescription(resultSet.getString("Description"));
				shrineAndTemple.setAreaInfo(resultSet.getString("Area_info"));
				shrineAndTemple.setMapLink(resultSet.getString("map_link"));
				shrineAndTemple.setImagePath(resultSet.getString("image_path"));

				shrineAndTemple.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
				shrineAndTemple.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
			} else {
				// リザルトセットが存在しない場合
				// 利用者インスタンスにnullを

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

	public List<ShrineAndTemple> searchByTag(ShrineAndTempleTag shrineAndTempleTag) throws Exception {

		return null;
	}

	public List<ShrineAndTemple> searchByName(String name) throws Exception {

		return null;
	}

}
