package tool;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import tool.Action;

public class QrGenerateAction extends Action {

    private static final int SIZE = 300; // QRコードサイズ(px)


	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		 // ==== 入力パラメータを取得 ====
        String url = req.getParameter("url");

        // ==== バリデーション ====
        if (url == null || url.trim().isEmpty()) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "URL パラメータが必要です");
            return;
        }

        try {
            // ==== QRコード生成 (ZXing) ====
            BitMatrix matrix = new MultiFormatWriter().encode(
                    url,
                    BarcodeFormat.QR_CODE,
                    SIZE,
                    SIZE
            );

            // ==== PNG でレスポンス ====
            res.setContentType("image/png");
            try (OutputStream out = res.getOutputStream()) {
                MatrixToImageWriter.writeToStream(matrix, "PNG", out);
            }

        } catch (Exception e) {
            e.printStackTrace();
            res.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "QRコード生成に失敗しました"
            );
        }
    }

}
