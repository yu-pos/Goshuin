package operator.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Operator;
import bean.ShrineAndTemple;
import bean.ShrineAndTempleTag;
import dao.ShrineAndTempleDao;
import dao.ShrineAndTempleTagDao;
import tool.Action;

public class ShrineAndTempleSearchExecuteForUpdateInfoAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Operator operator = (Operator)session.getAttribute("operator");

        // --- 変数 ---
        String searchStr;
        List<Integer> tagIdList = new ArrayList<>();
        List<ShrineAndTemple> results = new ArrayList<>();

        List<ShrineAndTempleTag> tagList = new ArrayList<>();
        Map<Integer, String> tagTypeMap = new HashMap<>();
        Map<Integer, List<ShrineAndTempleTag>> tagsByType = new HashMap<>();

        ShrineAndTempleDao shrineAndTempleDao = new ShrineAndTempleDao();
        ShrineAndTempleTagDao shrineAndTempleTagDao = new ShrineAndTempleTagDao();

        Map<String, String> errors = new HashMap<>();

        // --- 入力値取得 ---
        String[] selectedTags = req.getParameterValues("tag");
        searchStr = req.getParameter("name");

        // null → 空文字に統一（重要）
        if (searchStr == null) {
            searchStr = "";
        }

        // タグIDリスト作成
        if (selectedTags != null) {
            for (String val : selectedTags) {
                if (val != null && !val.isEmpty()) {
                    tagIdList.add(Integer.parseInt(val));
                }
            }
        }

        // --- 入力チェック（ここが正しい位置） ---
        if (tagIdList.isEmpty() && searchStr.isEmpty()) {
            errors.put("1", "タグ・名称のいずれかを入力してください");
        }

        // エラーがあれば検索画面へ戻す（return で処理を止める）
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);

            // ★ここを追加する（results を空リストとして渡す）
            req.setAttribute("results", new ArrayList<>());

            // ★検索結果 JSP に forward する
            req.getRequestDispatcher("shrine_and_temple_search_results_for_update_info.jsp").forward(req, res);
            return;

        }

        // --- DBからタグ一覧取得 ---
        tagList = shrineAndTempleTagDao.getall();

        // --- 検索実行 ---
        if (!tagIdList.isEmpty() && !searchStr.isEmpty()) {
            results = shrineAndTempleDao.searchByNameAndTag(searchStr, tagIdList);
        } else if (!tagIdList.isEmpty()) {
            results = shrineAndTempleDao.searchByTag(tagIdList);
        } else if (!searchStr.isEmpty()) {
            results = shrineAndTempleDao.searchByName(searchStr);
        }

        // --- タグ種別情報セット ---
        for (ShrineAndTempleTag tag : tagList) {

            if (tagIdList.contains(tag.getId())) {
                tag.setSelected(true);
            }

            tagTypeMap.put(tag.getTagTypeId(), tag.getTagTypeName());
            tagsByType.computeIfAbsent(tag.getTagTypeId(), k -> new ArrayList<>()).add(tag);
        }

        // --- 結果ごとのタグセット ---
        for (ShrineAndTemple result : results) {

            List<ShrineAndTempleTag> tagsForResult = shrineAndTempleTagDao.searchByShrineAndTemple(result.getId());
            Map<Integer, List<ShrineAndTempleTag>> tagsByTypeForResult = new HashMap<>();

            for (ShrineAndTempleTag tag : tagsForResult) {
                tagsByTypeForResult.computeIfAbsent(tag.getTagTypeId(), k -> new ArrayList<>()).add(tag);
            }

            result.setTagsByType(tagsByTypeForResult);
        }

        // --- JSPへ渡す ---
        req.setAttribute("results", results);
        req.setAttribute("selectedTags", selectedTags);
        req.setAttribute("searchStr", searchStr);
        req.setAttribute("tagsByType", tagsByType);
        req.setAttribute("tagTypeMap", tagTypeMap);

        // --- 結果画面へ ---
        req.getRequestDispatcher("shrine_and_temple_search_results_for_update_info.jsp").forward(req, res);
    }
}