package dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao extends Dao {

	/*
	 * 処理一覧
	 * ・利用者情報を更新(User:user) : boolean - Save(User:user) - 新規登録・使用中御朱印帳変更・御朱印カウント・ランクの更新をこれで行う
	 * ・IDで利用者情報を取得(id:int) : User - SearchById(id:int)
	 * ・ポイント付与(User:user, point:int) : boolean - AddPoint(User:user, point:int)
	 * ・ポイント消費(User:user, point:int) : boolean - SubPoint(User:user, point:int)
	 * ・ログインする(telNumber:String, password:String) : boolean - login(telNumber:String, password:String)
	 */

}
