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

	//実行中環境がローカルかEC2上か判定
	private static boolean isProd() {
	    String env = System.getenv("APP_ENV");
	    return "prod".equalsIgnoreCase(env);
	}

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

		String basePath;

		if (isProd()) {
			//EC2上のアップロード場所
			basePath = "/var/www/saved_images/" + dir + "/";
		} else {
			//ローカルのアップロード場所
		    basePath = req.getServletContext().getRealPath("/saved_images/" + dir + "/");
		}

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


	/**
	 * getBasePathメソッド 保存パスを出力
	 * JSP側で「${sessionScope.basePath}/(保存フォルダ名)/${imagePath}」などといった書き方で画像を指定可能
	 *
	 *
	 * @param なし
	 * @return 画像の保存されているパス
	 * @throws Exception
	 */
	public static String getBasePath() {

	    if (isProd()) {
	        return "/saved_images";  // Nginx/Tomcat mapping
	    } else {
	        return "/goshuin/saved_images";  // 静的フォルダをローカルで設定
	    }
	}


	 /**
     * deleteImageメソッド
     * 指定されたディレクトリとファイル名の画像を削除
     *
     * @param dir      保存ディレクトリ名
     * @param filename 削除したいファイル名
     * @return true: 削除成功 / false: 削除失敗（ファイルなし等）
     */
    public static boolean deleteImage(String dir, String filename, HttpServletRequest req) {

    	String basePath;
		if (isProd()) {
			//EC2上のアップロード場所
			basePath = "/var/www/saved_images/" + dir + "/";
		} else {
			//ローカルのアップロード場所
		    basePath = req.getServletContext().getRealPath("/saved_images/" + dir + "/");
		}

        File targetFile = new File(basePath, filename);

        System.out.println("[DEBUG] 削除対象ファイルの絶対パス: " + targetFile.getAbsolutePath());

        if (!targetFile.exists()) {
            System.out.println("[DEBUG] ファイルが存在しません: " + filename);
            return false;
        }

        boolean deleted = targetFile.delete();
        System.out.println("[DEBUG] 削除結果: " + deleted);

        return deleted;
    }


}
