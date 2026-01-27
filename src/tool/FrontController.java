package tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "*.action" })
@MultipartConfig
public class FrontController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			// パスを取得
			String path = req.getServletPath().substring(1);
			// ファイル名を取得しクラス名に変換
			String name = path.replace(".a", "A").replace('/', '.');
			// アクションクラスのインスタンスを返却
			Action action = (Action) Class.forName(name).getDeclaredConstructor().newInstance();

			// 遷移先URLを取得
			action.execute(req, res);


		} catch (Exception e) {
			StringWriter sw = new StringWriter();
	        PrintWriter pw = new PrintWriter(sw);
	        e.printStackTrace(pw); // スタックトレースをPrintWriterに書き込む
	        req.setAttribute("e", sw.toString()); ; // 文字列に変換 [1]
			// エラーページへリダイレクト
			req.getRequestDispatcher("error.jsp").forward(req, res);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doGet(req,res);

	}
}
