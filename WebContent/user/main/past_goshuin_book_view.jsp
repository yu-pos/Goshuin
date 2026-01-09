<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base.jsp">
  <c:param name="content">

    <link rel="stylesheet" href="/goshuin/user/css/past_goshuin_swipe.css" />
    <script src="/goshuin/user/scripts/past_goshuin_swipe.js"></script>

    <div class="page-header">
	  <button class="back-btn" onclick="location.href='PastGoshuinBookList.action'">
	    ← 戻る
	  </button>
	  <h1 class="page-title">過去の御朱印</h1>
	</div>


	<c:if test="${empty goshuinBook.goshuinList}">
	  <p>この御朱印帳にはまだ御朱印が登録されていません。</p>
	</c:if>

	<c:if test="${not empty goshuinBook.goshuinList}">
	  <div class="goshuin-gallery">
	    <div class="gallery-track">
	      <c:forEach var="owned" items="${goshuinBook.goshuinList}">
	        <img
	          src="${sessionScope.basePath}/goshuin/${owned.goshuin.imagePath}"
	          alt="${owned.goshuin.description}"
	          class="gallery-image" />
	      </c:forEach>
	    </div>
	  </div>
	</c:if>

  </c:param>
</c:import>
