package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Event;

public class EventDao extends Dao{


	//イベントの詳細情報の取得(eventId:int):Event

	//全てのイベント一覧を取得():list<event>
	//イベントを登録():boolean
	/**
	 * getByIdメソッド 利用者IDを指定して利用者インスタンスを1件取得する
	 *
	 * @param id:int
	 *            利用者ID
	 * @return 利用者クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public Event getById(int id) throws Exception {
		//shrineAndTempleDao.getById(取得したい神社仏閣のID)
		// イベントインスタンスを初期化
		Event event = new Event();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT * FROM event WHERE id=?");
			// プリペアードステートメントに神社仏閣IDをバインド
			statement.setInt(1, id);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();


			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// 利用者インスタンスに検索結果をセット

				event.setId(resultSet.getInt("id"));
				event.setTitle(resultSet.getString("title"));
				event.setText(resultSet.getString("text"));
	            event.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
				event.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

			} else {
				// リザルトセットが存在しない場合
				// 利用者インスタンスにnullを


				event= null;
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

		return event;
	}


	public List<Event> getAll() throws Exception {
		// リストを初期化
		List<Event> list = new ArrayList<>();
		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT * from event");

			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();


			while(resultSet.next())  {
				// リザルトセットが存在する場合
				// 御朱印帳インスタンスに検索結果をセット
                Event event = new Event();

				event.setId(resultSet.getInt("id"));
                event.setTitle(resultSet.getString("title"));
				event.setText(resultSet.getString("text"));
				event.setImagePath(resultSet.getString("image_path"));

				event.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
				event.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

				list.add(event);

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

	/**
	 *
	 *
	 * @param
	 *
	 * @return
	 * @throws
	 */
   public boolean insert(Event event) throws Exception {

       // コネクションを取得
       Connection connection = getConnection();
       PreparedStatement statement = null;
       int count = 0;

       try {
           // SQL文を準備（created_at / updated_at はDB側で現在時刻をセット）
           statement = connection.prepareStatement(
               "INSERT INTO event (title, text, image_path) " +
               "VALUES (?, ?, ?)"
           );

           // パラメータをバインド
           statement.setString(1, event.getTitle());
           statement.setString(2, event.getText());
           statement.setString(3, event.getImagePath());

           // 実行（INSERTなので executeUpdate）
           count = statement.executeUpdate();

       } catch (Exception e) {
           throw e;
       } finally {
           // PreparedStatement を閉じる
           if (statement != null) {
               try {
                   statement.close();
               } catch (SQLException sqle) {
                   throw sqle;
               }
           }

           // Connection を閉じる
           if (connection != null) {
               try {
                   connection.close();
               } catch (SQLException sqle) {
                   throw sqle;
               }
           }
       }

       // 成功したら true を返す
       return count > 0;
   }
   public boolean update(Event event) throws Exception {

		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;

		try {
		    statement = connection.prepareStatement(
		        "UPDATE event SET title=?, text=?, image_path=?, updated_at=NOW() WHERE id=?"
		    );

		    statement.setString(1, event.getTitle());
		    statement.setString(2, event.getText());
		    statement.setString(3, event.getImagePath());
		    statement.setInt(4, event.getId());

		    count = statement.executeUpdate();

		} finally {
		    if(statement != null) statement.close();
		    if(connection != null) connection.close();
		}

		return count > 0;
	}

}

