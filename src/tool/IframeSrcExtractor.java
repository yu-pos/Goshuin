package tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IframeSrcExtractor {

	private static final String MAP_EMBED_PREFIX = "https://www.google.com/maps/embed?pb=";

    public static String extractGoogleMapEmbedUrl(String iframeTag) {

    	 // ① すでに抽出済みのURLが渡された場合（そのまま返す）
        if (iframeTag != null && iframeTag.startsWith(MAP_EMBED_PREFIX)) {
            return iframeTag;
        }
        // src="～" を抽出する正規表現
        Pattern pattern = Pattern.compile("src\\s*=\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(iframeTag);

        if (matcher.find()) {
            String url = matcher.group(1);

            // 指定形式か判定
            if (url.startsWith("https://www.google.com/maps/embed?pb=")) {
                return url;
            }
        }

        return null; // 規定形式でない or src が無い場合
    }

}
