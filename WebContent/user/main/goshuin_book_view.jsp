<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base.jsp">
  <c:param name="content">

    <h1 class="page-title">御朱印帳</h1>

    <c:if test="${not empty message}">
      <p style="color:green;">${message}</p>
    </c:if>

    <c:if test="${empty goshuinBook}">
      <p>表示できる御朱印帳がありません。</p>
    </c:if>

    <c:if test="${not empty goshuinBook}">
      <!-- 表紙プレビュー -->
      <section class="goshuin-book-preview">
        <div class="goshuin-book-cover">
          <img
            src="/goshuin/saved_images/goshuin_book_design/${goshuinBook.goshuinBookDesign.imagePath}"
            alt="${goshuinBook.goshuinBookDesign.name}"
            class="goshuin-book-cover-img" />
        </div>
      </section>

      <!-- 御朱印一覧（必要なら） -->
      <section>
        <h2>この御朱印帳に登録されている御朱印</h2>
        <div class="goshuin-gallery">
          <div class="gallery-track">
            <c:forEach var="owned" items="${goshuinBook.goshuinList}">
			  <c:if test="${not empty owned.goshuin.imagePath}">
			    <%-- alt は description を使う --%>
			    <img
			      src="/goshuin/saved_images/goshuin/${owned.goshuin.imagePath}"
			      alt="${owned.goshuin.description}"
			      class="goshuin-img" />
			  </c:if>
			</c:forEach>
          </div>
        </div>
      </section>

      <!-- ボタン -->
      <div class="kasutamubtn-row">
        <a href="PastGoshuinBookList.action" class="nav-btn custom-left">一覧</a>
        <!-- 👍 ここから色選択画面へ -->
        <a href="GoshuinBookEdit.action?bookId=${goshuinBook.id}"
           class="nav-btn custom-right">編集</a>
      </div>
    </c:if>

  </c:param>
</c:import>
