package operator.main;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.lang3.tuple.Pair;
import org.h2.jdbc.JdbcSQLException;

import bean.ShrineAndTemple;
import bean.ShrineAndTempleTag;
import dao.ShrineAndTempleDao;
import dao.ShrineAndTempleTagDao;
import tool.Action;
import tool.IframeSrcExtractor;
import tool.ImageUtils;

public class ShrineAndTempleRegistExecuteAction extends Action {

	//実行中環境がローカルかEC2上か判定
	private static boolean isProd() {
	    String env = System.getenv("APP_ENV");
	    return "prod".equalsIgnoreCase(env);
	}

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        String imagePath = null;

        ShrineAndTemple shrineAndTemple = new ShrineAndTemple();
        List<ShrineAndTempleTag> chooseTagList = new ArrayList<>();

        Map<Integer, String> tagTypeMap = new HashMap<>();
        Map<Integer, List<ShrineAndTempleTag>> tagsByType = new HashMap<>();

        ShrineAndTempleDao shrineDao = new ShrineAndTempleDao();
        ShrineAndTempleTagDao tagDao = new ShrineAndTempleTagDao();

        Map<String, String> errors = new HashMap<>();

        // ===== パラメータ取得 =====
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String[] tagIds = req.getParameterValues("tag"); // ★編集画面と同じ
        String description = req.getParameter("description");
        String areaInfo = req.getParameter("areaInfo");
        String mapLink = req.getParameter("mapLink");
        Part image = req.getPart("image");

        // ===== タグ一覧取得（画面再表示用）=====
        List<ShrineAndTempleTag> allTags = tagDao.getall();

        // タグ種別のマップ化
        for (ShrineAndTempleTag tag : allTags) {
            tagTypeMap.put(tag.getTagTypeId(), tag.getTagTypeName());
            tagsByType.computeIfAbsent(tag.getTagTypeId(), k -> new ArrayList<>()).add(tag);
        }

        // ===== タグ必須チェック（種別の数だけ選ばれてる必要がある想定）=====
        int requiredTypeCount = tagTypeMap.size();

        if (tagIds == null || tagIds.length != requiredTypeCount) {
            errors.put("1", "タグを指定してください");
        } else {
            // 選択されたタグをBean化
            for (String tagId : tagIds) {
                if (tagId == null || tagId.isEmpty()) {
                    errors.put("1", "タグを指定してください");
                    break;
                }
                try {
                    chooseTagList.add(tagDao.getById(Integer.parseInt(tagId)));
                } catch (Exception e) {
                    errors.put("1", "タグを指定してください");
                    break;
                }
            }
        }

        // ===== エラー時のselected復元（編集画面方式）=====
        if (tagIds != null) {
            for (ShrineAndTempleTag tag : allTags) {
                tag.setSelected(false);
                for (String tagId : tagIds) {
                    if (tagId != null && !tagId.isEmpty() && tag.getId() == Integer.parseInt(tagId)) {
                        tag.setSelected(true);
                        break;
                    }
                }
            }
        }

        // ===== Google Map URL抽出 =====
        String extractedMapLink = IframeSrcExtractor.extractGoogleMapEmbedUrl(mapLink);

        // embed URL 直貼りも許可
        if (extractedMapLink == null && mapLink != null && mapLink.contains("www.google.com/maps/embed")) {
            extractedMapLink = mapLink;
        }

        if (extractedMapLink == null) {
            errors.put("2", "埋め込みリンクの指定が不正です。");
            extractedMapLink = mapLink; // 入力保持
        }

        // ===== 画像保存（エラーがない時だけ）=====
        if (errors.isEmpty()) {
            imagePath = ImageUtils.saveImage(image, "shrine_and_temple", req);
            if (imagePath == null) {
                errors.put("3", "画像のアップロードに失敗しました。");
            }
        }

        // ===== Beanにセット（エラーでも保持）=====
        shrineAndTemple.setName(name);
        shrineAndTemple.setAddress(address);
        shrineAndTemple.setDescription(description);
        shrineAndTemple.setAreaInfo(areaInfo);
        shrineAndTemple.setMapLink(extractedMapLink);
        shrineAndTemple.setImagePath(imagePath);
        shrineAndTemple.setTagList(chooseTagList);

        // ===== DB登録 =====
        if (errors.isEmpty()) {
            try {
                Pair<Boolean, Integer> result = shrineDao.insert(shrineAndTemple);

                if (!result.getLeft()) {
                    errors.put("0", "神社仏閣情報の登録に失敗しました");
                    ImageUtils.deleteImage("shrine_and_temple", imagePath, req);
                } else {
                    int id = result.getRight();

                    String serverUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort();

                    // QRコード
                    // 正常動作しなくなったので応急処置しています
                    String qrTargetUrl = "";
                    if (isProd()) {
                    	qrTargetUrl = "https://goshuin.ddns.net/goshuin"
                    		+ "/user/main/GoshuinChoose.action?shrineAndTempleId=" + id;
                    } else {
                    	qrTargetUrl = serverUrl + req.getContextPath()
                			+ "/user/main/GoshuinChoose.action?shrineAndTempleId=" + id;
                    }

                    req.setAttribute("qrTargetUrl", qrTargetUrl);
                    req.setAttribute("qrImageUrl",
                            req.getContextPath() + "/tool/QrGenerate.action?url="
                                    + URLEncoder.encode(qrTargetUrl, "UTF-8"));
                }

            } catch (JdbcSQLException e) {
                if ("23505".equals(e.getSQLState())) {
                    errors.put("4", "神社名が重複しています");
                } else {
                    errors.put("0", "神社仏閣情報の登録に失敗しました");
                }
                ImageUtils.deleteImage("shrine_and_temple", imagePath, req);
            }
        }

        // ===== 画面へ返す =====
        req.setAttribute("shrineAndTemple", shrineAndTemple);
        req.setAttribute("tagTypeMap", tagTypeMap);
        req.setAttribute("tagsByType", tagsByType);

        if (errors.isEmpty()) {
            req.getRequestDispatcher("shrine_and_temple_regist_complete.jsp").forward(req, res);
        } else {
           req.setAttribute("errors", errors);

           // ★ tagsByType/tagTypeMap は ExecuteAction 側で selected 付きで作ってあるので
           // ★ そのまま JSP に戻す（Regist.action を経由しない）
           req.getRequestDispatcher("shrine_and_temple_regist.jsp").forward(req, res);
        }
    }
}
