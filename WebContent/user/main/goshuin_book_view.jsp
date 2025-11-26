<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base.jsp">
  <c:param name="content">

    <!-- ★ ここに置けばちゃんと読み込まれる -->
    <link rel="stylesheet" href="/goshuin/user/css/goshuin_book_view.css" />

    <h1 class="page-title">御朱印帳</h1>

    <c:if test="${not empty message}">
      <p style="color:green;">${message}</p>
    </c:if>

    <c:if test="${empty goshuinBook}">
      <p>表示できる御朱印帳がありません。</p>
    </c:if>

    <c:if test="${not empty goshuinBook}">

      <!-- ▼ 御朱印帳プレビュー -->
      <section class="goshuin-book-preview">
        <div class="goshuin-book-cover">
          <!-- 表紙画像 -->
          <img
            src="/goshuin/saved_images/goshuin_book_design/${goshuinBook.goshuinBookDesign.imagePath}"
            alt="${goshuinBook.goshuinBookDesign.name}"
            class="goshuin-book-cover-img" />

          <!-- 保存されたステッカー -->
          <c:forEach var="att" items="${goshuinBook.attachedStickerList}">
            <img
              src="/goshuin/saved_images/sticker/${att.goshuinBookSticker.imagePath}"
              alt="${att.goshuinBookSticker.name}"
              class="placed-sticker"
              style="left:${att.xPos}%; top:${att.yPos}%;" />
          </c:forEach>
        </div>
      </section>

      <!-- 御朱印一覧（今まで通り） -->
      <section>
        <h2>この御朱印帳に登録されている御朱印</h2>
        <div class="goshuin-gallery">
          <div class="gallery-track">
            <c:forEach var="owned" items="${goshuinBook.goshuinList}">
              <c:if test="${not empty owned.goshuin.imagePath}">
                <img
                  src="/goshuin/saved_images/goshuin/${owned.goshuin.imagePath}"
                  alt="${owned.goshuin.description}"
                  class="goshuin-img" />
              </c:if>
            </c:forEach>
          </div>
        </div>
      </section>

      <div class="kasutamubtn-row">
        <a href="PastGoshuinBookList.action" class="nav-btn custom-left">一覧</a>
        <a href="GoshuinBookEdit.action?bookId=${goshuinBook.id}"
           class="nav-btn custom-right">編集</a>
      </div>

    </c:if>

  </c:param>
</c:import>
