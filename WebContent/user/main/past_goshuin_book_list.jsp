<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base.jsp">
  <c:param name="content">

    <link rel="stylesheet" href="/goshuin/user/css/past_goshuin_book_list.css" />

    <h1 class="page-title">過去の御朱印帳一覧</h1>

    <c:if test="${empty bookList}">
      <p class="empty-message">過去の御朱印帳はありません。</p>
    </c:if>

    <c:forEach var="book" items="${bookList}">
      <div class="past-book-card">

        <!-- ★ 表紙＋ステッカーを重ね描画 -->
        <div class="past-book-cover">
          <img
            src="/goshuin/saved_images/goshuin_book_design/${book.goshuinBookDesign.imagePath}"
            alt="${book.goshuinBookDesign.name}"
            class="past-book-cover-img" />

          <c:forEach var="att" items="${book.attachedStickerList}">
            <img
              src="/goshuin/saved_images/sticker/${att.goshuinBookSticker.imagePath}"
              alt="${att.goshuinBookSticker.name}"
              class="past-book-sticker"
              style="left:${att.xPos}%; top:${att.yPos}%;" />
          </c:forEach>
        </div>

        <div class="past-book-buttons">
          <a href="PastGoshuinBookView.action?bookId=${book.id}" class="btn view-btn">
            御朱印を見る
          </a>
          <a href="GoshuinBookEdit.action?bookId=${book.id}" class="btn edit-btn">
            編集
          </a>
        </div>
      </div>

      <div class="return-current-book">
	  <a href="GoshuinBookView.action">現在の御朱印帳へ戻る</a>
	</div>

    </c:forEach>

  </c:param>
</c:import>
