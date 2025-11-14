package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ShrineAndTempleTag;

public class ShrineAndTempleTagDao extends Dao {



		//全てのタグ一覧とタグ種別一覧を取得():list<shurainAndTempleTag>
		//神社仏閣からタグを検索():list<shrineAndTemple>


		public   List<ShrineAndTempleTag> getall() throws Exception {


			// リストを初期化
			List<ShrineAndTempleTag> list = new ArrayList<>();
			// データベースへのコネクションを確立
			Connection connection = getConnection();
			// プリペアードステートメント
			PreparedStatement statement = null;

			try {
				statement = connection.prepareStatement("SELECT shrine_and_temple_tag.id as tag_id, "
						+ "shrine_and_temple_tag.name as tag_name,  "
						+"shrine_and_temple_tag.tag_type_id as tag_type_id, "
						+ "shrine_and_temple_tag_type.name as tag_type_name"
						+"shrine_and_temple_tag.updated_at as updated_at, "
						+ "shrine_and_temple_tag.created_at as created_at "
						+"FROM shrine_and_temple_tag"
						+"JOIN shrine_and_temple_tag_type ON  shrine_and_temple_tag.tag_type_id ="
						+ " shrine_and_temple_tag_type.id;"
						);

				// プリペアードステートメントを実行
				ResultSet resultSet = statement.executeQuery();

				while (resultSet.next()) {

					ShrineAndTempleTag shrineAndTempleTag = new ShrineAndTempleTag();

					// 利用者インスタンスに検索結果をセット
					shrineAndTempleTag.setId(resultSet.getInt("tag_id"));
					shrineAndTempleTag.setTagTypeId(resultSet.getInt("tag_type_id"));
					shrineAndTempleTag.setName(resultSet.getString("tag_name"));
					shrineAndTempleTag.setTagTypeName(resultSet.getString("tag_type_name"));
					shrineAndTempleTag.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
					shrineAndTempleTag.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

	        		list.add(shrineAndTempleTag);

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


		  public List<ShrineAndTempleTag>   searchByShrineAndTemple(int shrineAndTempleId) throws Exception{


			// リストを初期化
				List<ShrineAndTempleTag> list = new ArrayList<>();
		    	// コネクションを確立
		        Connection connection = getConnection();
		        // プリペアードステートメント
		        PreparedStatement statement = null;

		        try {
		            // プリペアードステートメントにSQL文をセット（神社仏閣IDで検索）
		            statement = connection.prepareStatement(
		            		"SELECT b.tag_id as tag_id, b.tag_name as tag_name, b.tag_type_id as tag_type_id, b.tag_type_name as tag_type_name, b.updated_at as updated_at, b.created_at as created_at"
		            				+"FROM shrine_and_temple_tagging"
		            				+"JOIN ("
		            				+"SELECT shrine_and_temple_tag.id as tag_id, shrine_and_temple_tag.name as tag_name,  shrine_and_temple_tag.tag_type_id as tag_type_id, shrine_and_temple_tag_type.name as tag_type_name, shrine_and_temple_tag.updated_at as updated_at, shrine_and_temple_tag.created_at as created_at "
		            				+"FROM shrine_and_temple_tag"
		            				+"JOIN shrine_and_temple_tag_type ON  shrine_and_temple_tag.tag_type_id = shrine_and_temple_tag_type.id) as b"
		            				+"ON shrine_and_temple_tagging.tag_id = b.tag_id"
		            				+"WHERE shrine_and_temple_tagging.shrine_and_temple_id = ?;"
		            );

		            // プリペアードステートメントに神社仏閣IDをバインド
		            statement.setInt(1, shrineAndTempleId);
		            // プリペアードステートメントを実行
		            ResultSet resultSet = statement.executeQuery();

		            while (resultSet.next()) {


		            	ShrineAndTempleTag shrineAndTempleTag = new ShrineAndTempleTag();

				    	// 利用者インスタンスに検索結果をセット
						shrineAndTempleTag.setId(resultSet.getInt("tag_id"));
						shrineAndTempleTag.setTagTypeId(resultSet.getInt("tag_type_id"));
						shrineAndTempleTag.setName(resultSet.getString("tag_name"));
						shrineAndTempleTag.setTagTypeName(resultSet.getString("tag_type_name"));
						shrineAndTempleTag.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
						shrineAndTempleTag.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

		        		list.add(shrineAndTempleTag);
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



























}