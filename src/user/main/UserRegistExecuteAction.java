package user.main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import dao.UserDao;
import tool.Action;

public class UserRegistExecuteAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

				// ローカル変数の指定 1
				String userName   = ""; // 入力されたユーザー名
				String realName   = ""; // 入力された氏名
				String birthDateStr = ""; // 入力された生年月日（文字列）
				String address    = ""; // 入力された住所
				String tel        = ""; // 入力された電話番号
				String password   = ""; // 入力されたパスワード

				User user         = new User();
				UserDao userDao   = new UserDao();
				Map<String, String> errors = new HashMap<>(); // エラーメッセージ

				// リクエストパラメーターの取得 2
				userName     = req.getParameter("userName");
				realName     = req.getParameter("realName");
				birthDateStr = req.getParameter("birthDate");  // JSP 側の name="birthDate" を想定
				address      = req.getParameter("address");
				tel          = req.getParameter("tel");
				password     = req.getParameter("password");

				// DBからデータ取得 3
				// なし

				// ビジネスロジック 4


				// 電話番号チェック（有効な電話番号でなかった場合、代替フロー②）
				if (!isEmpty(tel) && !tel.matches("\\d{10,11}")) {
					errors.put("1", "有効な電話番号を入力してください");
				}

				// 電話番号重複チェック（既に登録がある場合）
				if (errors.isEmpty()) { // 他のエラーがないときだけ DB アクセス
					if (userDao.getByTel(tel) != null) {
						errors.put("2", "この電話番号は既に登録されています");
					}
				}

				// 生年月日の形式チェック（"YYYY-MM-DD" を想定）
				LocalDateTime birthDateTime = null;
				if (!isEmpty(birthDateStr)) {
					try {
						LocalDate birthDate = LocalDate.parse(birthDateStr); // 例：2025-01-01
						birthDateTime = birthDate.atStartOfDay(); // LocalDateTime に変換（00:00:00）
					} catch (Exception e) {
						// パースに失敗したらエラー
						errors.put("3", "生年月日を正しい形式で入力してください");
					}
				}

				// エラーがある場合
				if (!errors.isEmpty()) {
					// リクエストにエラーメッセージをセット
					req.setAttribute("errors", errors);

					// レスポンス値をセット 6（入力値を戻す）
					req.setAttribute("userName",   userName);
					req.setAttribute("realName",   realName);
					req.setAttribute("birthDate",  birthDateStr);
					req.setAttribute("address",    address);
					req.setAttribute("tel",        tel);

					// JSPへフォワード 7（登録画面に戻る）
					req.getRequestDispatcher("UserRegist.action").forward(req, res);
					return;
				}

				// エラーが無い場合は user に値をセット
				user.setUserName(userName);
				user.setRealName(realName);
				user.setBirthDate(birthDateTime);  // LocalDateTime
				user.setAddress(address);
				user.setTelNumber(tel);
				user.setPassword(password);

				// save / insert 実行（御朱印帳発行も内部でやってくれる）
				boolean result = userDao.insert(user);

				// レスポンス値をセット 6（成功・失敗どちらでも入力値は保持してもOK）
				req.setAttribute("userName",   userName);
				req.setAttribute("realName",   realName);
				req.setAttribute("birthDate",  birthDateStr);
				req.setAttribute("address",    address);
				req.setAttribute("tel",        tel);

				if (result) {
				    // 登録完了画面にフォワード
				    req.getRequestDispatcher("user_regist_complete.jsp").forward(req, res);
				} else {
				    errors.put("5", "登録に失敗しました");
				    req.setAttribute("errors", errors);
				    req.getRequestDispatcher("UserRegist.action").forward(req, res);
				}
			}

			// 空文字チェック用のメソッド（Student版にはなかったけど共通処理として追加）
			private boolean isEmpty(String s) {
				return s == null || s.trim().isEmpty();
			}


}
