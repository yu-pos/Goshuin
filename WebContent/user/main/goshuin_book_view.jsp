<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base.jsp">
  <c:param name="content">

    <!-- この画面専用の大きめ表示CSS -->
    <link rel="stylesheet" href="/goshuin/user/css/goshuin_book_view_large.css" />

    <h1 class="page-title">御朱印帳</h1>

    <!-- デバッグ用に一旦IDを出しておく -->
    <c:if test="${not empty goshuinBook}">
    </c:if>

    <!-- 御朱印帳がない場合 -->
    <c:if test="${empty goshuinBook}">
      <p>表示できる御朱印帳がありません。</p>
    </c:if>

    <!-- 御朱印帳がある場合 -->
    <c:if test="${not empty goshuinBook}">
      <section class="goshuin-book-view-section">
        <h2>現在の御朱印帳</h2>

        <div class="goshuin-book-view-cover-large">
          <img
            src="/goshuin/saved_images/goshuin_book_design/${goshuinBook.goshuinBookDesign.imagePath}"
            alt="${goshuinBook.goshuinBookDesign.name}"
            class="goshuin-book-view-cover-img" />
        </div>

        <!-- ▼ ここから追加：現在の御朱印帳に登録されている御朱印一覧 -->
		<c:if test="${not empty goshuinBook.goshuinList}">
		  <section class="current-book-goshuin">
		    <h2>この御朱印帳の御朱印</h2>

		    <div class="goshuin-list">
		      <c:forEach var="owned" items="${goshuinBook.goshuinList}">
		        <div class="goshuin-item">
		          <img
		            src="/goshuin/saved_images/goshuin/${owned.goshuin.imagePath}"
		            alt="${owned.goshuin.description}"
		            class="goshuin-img" />
		          <div class="goshuin-name">
		            ${owned.goshuin.description}
		          </div>
		        </div>
		      </c:forEach>
		    </div>
		  </section>
		</c:if>

		<c:if test="${empty goshuinBook.goshuinList}">
		  <section class="current-book-goshuin">
		    <h2>この御朱印帳の御朱印</h2>
		    <p>この御朱印帳にはまだ御朱印が登録されていません。</p>
		  </section>
		</c:if>

        <!-- ここはお好みで：御朱印帳編集などのボタン -->
        <div class="goshuin-book-view-buttons">
          <a href="GoshuinBookEdit.action" class="btn">表紙デザインの変更</a>
          <a href="PastGoshuinBookList.action" class="btn">過去の御朱印帳一覧</a>
        </div>
      </section>
    </c:if>

  </c:param>
</c:import>
