package user.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.GoshuinBook;
import bean.OwnedGoshuinBookDesignGroup;
import bean.RegdGoshuinBookDesign;
import bean.User;
import dao.GoshuinBookDao;
import dao.OwnedGoshuinBookDesignGroupDao;
import dao.RegdGoshuinBookDesignDao;
import tool.Action;

public class GoshuinBookEditAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ★ ここは必ず「/」から始める（コンテキスト直下）
        String url = "/user/main/goshuin_book_edit.jsp";

        HttpSession session = req.getSession(false);
        if (session == null) {
            // ★ ログイン画面も同様に「/」から
            url = "/user/main/login.jsp";
            req.getRequestDispatcher(url).forward(req, res);
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            url = "/user/main/login.jsp";
            req.getRequestDispatcher(url).forward(req, res);
            return;
        }

        // パラメータから bookId（URL に付けている）を取得
        int bookId;
        try {
            bookId = Integer.parseInt(req.getParameter("bookId"));
        } catch (Exception e) {
            // 取得できなければアクティブ御朱印帳
            if (user.getActiveGoshuinBook() != null) {
                bookId = user.getActiveGoshuinBook().getId();
            } else {
                req.setAttribute("error", "編集対象の御朱印帳が指定されていません。");
                req.getRequestDispatcher(url).forward(req, res);
                return;
            }
        }

        GoshuinBookDao gdao = new GoshuinBookDao();
        GoshuinBook goshuinBook = gdao.getById(bookId);

        if (goshuinBook == null) {
            req.setAttribute("error", "御朱印帳が見つかりません。");
        } else {
            req.setAttribute("goshuinBook", goshuinBook);
        }

        // 登録済み表紙デザイン一覧
        OwnedGoshuinBookDesignGroupDao ownedGoshuinBookDesignGroupDao = new OwnedGoshuinBookDesignGroupDao();
        List<OwnedGoshuinBookDesignGroup> designGroupList = ownedGoshuinBookDesignGroupDao.getByUser(user.getId());

        List<RegdGoshuinBookDesign> designList = new ArrayList<>();

        RegdGoshuinBookDesignDao regdGoshuinBookDesignDao = new RegdGoshuinBookDesignDao();

        for (OwnedGoshuinBookDesignGroup designGroup : designGroupList) {
        	designList.addAll(regdGoshuinBookDesignDao.searchByGroup(designGroup.getGoshuinBookDesignGroup().getId()));
        }

        req.setAttribute("designList", designList);

        req.getRequestDispatcher(url).forward(req, res);
    }
}
