package user.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.GoshuinBook;
import bean.OwnedGoshuin;
import dao.GoshuinBookDao;
import tool.Action;

public class PastGoshuinBookViewAction extends Action {

    private static final int PAGE_SIZE = 10;   // 1ページ10件

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        int bookId = Integer.parseInt(req.getParameter("bookId"));

        GoshuinBookDao bookDao = new GoshuinBookDao();
        GoshuinBook book = bookDao.getById(bookId);

        if (book == null) {
            req.setAttribute("error", "指定された御朱印帳が見つかりませんでした。");
            req.getRequestDispatcher("past_goshuin_book_list.jsp")
               .forward(req, res);
            return;
        }

        List<OwnedGoshuin> all = book.getGoshuinList();
        List<List<OwnedGoshuin>> pages = new ArrayList<>();

        for (int i = 0; i < all.size(); i += PAGE_SIZE) {
            int toIndex = Math.min(i + PAGE_SIZE, all.size());
            pages.add(new ArrayList<>(all.subList(i, toIndex)));
        }

        req.setAttribute("goshuinBook", book);
        req.setAttribute("pages", pages);

        req.getRequestDispatcher("past_goshuin_book_view.jsp")
           .forward(req, res);
    }
}
