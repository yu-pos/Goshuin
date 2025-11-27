<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base.jsp">
  <c:param name="content">

  	<link rel="stylesheet" href="/goshuin/user/css/past_goshuin_book_view.css" />

    <h1 class="page-title">御朱印一覧</h1>

    <c:if test="${not empty error}">
      <p style="color:red;">${error}</p>
    </c:if>

    <c:if test="${empty pages}">
      <p>この御朱印帳にはまだ御朱印が登録されていません。</p>
    </c:if>

    <c:if test="${not empty pages}">
      <section class="goshuin-gallery">
        <div class="gallery-track">

          <!-- 1ページずつスワイプで切り替え -->
          <c:forEach var="page" items="${pages}">
            <div class="goshuin-page">
              <c:forEach var="owned" items="${goshuinBook.goshuinList}">
			  <div class="goshuin-item">

			    <img
			      src="/goshuin/saved_images/goshuin/${owned.goshuin.imagePath}"
			      alt="御朱印"
			      class="goshuin-img">
			  </div>
			</c:forEach>
            </div>
          </c:forEach>

        </div>
      </section>
    </c:if>

    <div class="form-buttons">
      <a href="PastGoshuinBookList.action" class="btn btn-sub">御朱印帳一覧へ戻る</a>
    </div>

  </c:param>
</c:import>
