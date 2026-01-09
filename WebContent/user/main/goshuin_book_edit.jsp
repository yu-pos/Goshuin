<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base.jsp">
  <c:param name="content">

    <!-- この画面専用 CSS / JS -->
    <link rel="stylesheet" href="/goshuin/user/css/goshuin_book_color.css" />
    <script src="/goshuin/user/scripts/goshuin_book_color.js"></script>

    <h1 class="page-title">御朱印帳 表紙デザイン選択</h1>

    <c:if test="${not empty error}">
      <p style="color:red;">${error}</p>
    </c:if>

    <c:if test="${empty goshuinBook}">
      <p>編集できる御朱印帳がありません。</p>
    </c:if>

    <c:if test="${not empty goshuinBook}">
      <form action="GoshuinBookEditExecute.action" method="post" id="bookEditForm">
        <input type="hidden" name="bookId" value="${goshuinBook.id}" />

        <!-- 現在の御朱印帳プレビュー -->
        <section class="goshuin-book-preview">
          <h2>現在の御朱印帳</h2>
          <div class="goshuin-book-cover">
            <img
              id="currentBookImg"
              src="${sessionScope.basePath}/goshuin_book_design/${goshuinBook.goshuinBookDesign.imagePath}"
              alt="${goshuinBook.goshuinBookDesign.name}"
              class="goshuin-book-cover-img" />
          </div>
        </section>

        <!-- デザイン選択 -->
        <section class="design-select">
          <h2>表紙デザイン</h2>
          <div class="design-list">
            <c:forEach var="d" items="${designList}">
              <label class="design-item">
                <input
                  type="radio"
                  class="design-radio"
                  name="designId"
                  value="${d.id}"
                  data-image-path="${sessionScope.basePath}/goshuin_book_design/${d.imagePath}"
                  data-design-name="${d.name}"
                  <c:if test="${goshuinBook.goshuinBookDesign.id == d.id}">checked</c:if>
                />
                <div class="design-content">
                  <img
                    src="${sessionScope.basePath}/goshuin_book_design/${d.imagePath}"
                    alt="${d.name}"
                    class="design-thumb" />
                  <div class="design-name">${d.name}</div>
                  <span class="design-select-label">選択</span>
                </div>
              </label>
            </c:forEach>
          </div>
        </section>

        <div class="form-buttons">
          <!-- 次へ：表紙デザインを保存してステッカー画面へ -->
          <input type="submit" value="ステッカー貼付画面へ" />
          <a href="GoshuinBookEditCancel.action">キャンセル</a>
        </div>
      </form>
    </c:if>

  </c:param>
</c:import>
