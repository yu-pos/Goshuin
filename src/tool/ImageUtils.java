package tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class ImageUtils {


	/**
	 * saveImageメソッド 画像とディレクトリ名を指定してアップロードされた画像を保存
	 *
	 * @param image : Part
	 *            画像ファイル
	 *         dir : String
	 *            画像を保存したいディレクトリ（フォルダ）名
	 * @return 生成されたファイル名
	 * @throws Exception
	 */
	public static String saveImage(Part image, String dir, HttpServletRequest req) throws IOException {


	    String basePath = req.getServletContext().getRealPath("/saved_images/" + dir + "/"); //保存ディレクトリ
		File uploadDir = new File(basePath);
		//.exists() で「そのフォルダがすでにあるか？」をチェック
	    //.mkdirs() は、なければフォルダを自動で作る命令
	    if (!uploadDir.exists()) uploadDir.mkdirs();

	    //ファイル名を取得
	    String fileName = Paths.get(image.getSubmittedFileName()).getFileName().toString();

		//拡張子を取得
	    int point = fileName.lastIndexOf(".");
	    String extension = fileName.substring(point);


	    //ファイル名をUUIDで命名
	    String savedFilename = UUID.randomUUID().toString() + extension;

	    File outputFile = new File(uploadDir, savedFilename);

	    // 保存前に絶対パスを出力
	    System.out.println("[DEBUG] 画像の保存先（絶対パス）: " + outputFile.getAbsolutePath());
	    System.out.println("[DEBUG] 画像の保存先の存在確認（parent）: " + outputFile.getParentFile().exists());

	    //画像を保存
	    try (InputStream in = image.getInputStream();
	            FileOutputStream out = new FileOutputStream(outputFile)) {

	           byte[] buffer = new byte[8192];
	           int len;

	           while ((len = in.read(buffer)) != -1) {
	               out.write(buffer, 0, len);
	           }
	       }



	    return savedFilename;
	}

}
