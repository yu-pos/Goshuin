package operator.main;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ShrineAndTemple;
import bean.ShrineAndTempleTag;
import dao.ShrineAndTempleDao;
import dao.ShrineAndTempleTagDao;
import tool.Action;

public class ShrineAndTempleUpdateAction extends Action {

	//実行中環境がローカルかEC2上か判定
	private static boolean isProd() {
	    String env = System.getenv("APP_ENV");
	    return "prod".equalsIgnoreCase(env);
	}

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {


		//ローカル変数の宣言 1
		int shrineAndTempleId;
		ShrineAndTemple shrineAndTemple = new ShrineAndTemple();

		List<ShrineAndTempleTag> tagList = new ArrayList<>();
		Map<Integer, String> tagTypeMap = new HashMap<>();
		Map<Integer, List<ShrineAndTempleTag>> tagsByType = new HashMap<>();

		List<ShrineAndTempleTag> selectedTagList = new ArrayList<>();

		ShrineAndTempleDao shrineAndTempleDao = new ShrineAndTempleDao();
		ShrineAndTempleTagDao shrineAndTempleTagDao = new ShrineAndTempleTagDao();





		//リクエストパラメータ―の取得 2
		shrineAndTempleId = Integer.parseInt(req.getParameter("shrineAndTempleId"));


		//DBからデータ取得 3
		shrineAndTemple = shrineAndTempleDao.getById(shrineAndTempleId);
		selectedTagList = shrineAndTemple.getTagList();

		tagList = shrineAndTempleTagDao.getall();


		//ビジネスロジック 4
		//タグ種別情報を取得
		for (ShrineAndTempleTag tag : tagList) {
			tagTypeMap.put(tag.getTagTypeId(), tag.getTagTypeName());
		    tagsByType.computeIfAbsent(tag.getTagTypeId(), k -> new ArrayList<>()).add(tag);
		}


		// 1. selectedTagList から選択済みタグIDのセットを作る
		Set<Integer> selectedIds = new HashSet<>();
		for (ShrineAndTempleTag selected : selectedTagList) {
		    selectedIds.add(selected.getId());
		}

		// 2. tagList のタグが selectedIds に含まれていれば isSelected = true
		for (ShrineAndTempleTag tag : tagList) {
		    if (selectedIds.contains(tag.getId())) {
		        tag.setSelected(true);
		    } else {
		        tag.setSelected(false);
		    }
		}

		//QRコード生成URLを取得

        // サーバ自身のURL取得
        String serverUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort();



        // QRコード化するターゲットURL
        // 正常動作しなくなったので応急処置しています
        String qrTargetUrl = "";
        if (isProd()) {
        	qrTargetUrl = "https://goshuin.ddns.net/goshuin"
        		+ "/user/main/GoshuinChoose.action?shrineAndTempleId=" + shrineAndTempleId;
        } else {
        	qrTargetUrl = serverUrl + req.getContextPath()
    			+ "/user/main/GoshuinChoose.action?shrineAndTempleId=" + shrineAndTempleId;
        }

        // JSP に渡す
        req.setAttribute("qrTargetUrl", qrTargetUrl);

        // QRコード画像サーブレットへのURL
        String qrImageUrl = req.getContextPath() + "/tool/QrGenerate.action?url="
    			+ URLEncoder.encode(qrTargetUrl, "UTF-8");

        req.setAttribute("qrImageUrl", qrImageUrl);

		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		req.setAttribute("shrineAndTemple", shrineAndTemple);
		req.setAttribute("tagsByType", tagsByType);
		req.setAttribute("tagTypeMap", tagTypeMap);

		//JSPへフォワード 7
		req.getRequestDispatcher("shrine_and_temple_update.jsp").forward(req, res);
	}
}
