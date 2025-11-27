<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base.jsp">
  <c:param name="content">

    <!-- この画面専用 CSS -->
    <link rel="stylesheet" href="/goshuin/user/css/past_goshuin_book_list.css" />

    <h1 class="page-title">過去の御朱印帳一覧</h1>

    <!-- 0冊の場合 -->
    <c:if test="${empty bookList}">
      <p>過去の御朱印帳はありません。</p>
    </c:if>

    <!-- 過去の御朱印帳がある場合 -->
    <c:if test="${not empty bookList}">
      <div class="book-list">

        <c:forEach var="b" items="${bookList}">
          <div class="book-item">

            <!-- 表紙画像 -->
            <div class="book-cover">
              <img src="/goshuin/saved_images/goshuin_book_design/${b.goshuinBookDesign.imagePath}"
                   alt="${b.goshuinBookDesign.name}"
                   class="book-cover-img">
            </div>

            <!-- ボタン -->
            <div class="book-buttons">
              <a href="PastGoshuinBookView.action?bookId=${b.id}" class="btn">御朱印を見る</a>
              <a href="GoshuinBookStickerEdit.action?bookId=${b.id}" class="btn">編集</a>
            </div>

          </div>
        </c:forEach>

      </div>
    </c:if>

  </c:param>
</c:import>
