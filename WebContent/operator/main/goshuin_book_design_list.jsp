<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h3>御朱印帳登録一覧</h3>

      <div class="register-header">
        <div class="register-title">御朱印帳表紙デザイン一覧</div>
        <a href="GoshuinBookDesignRegist.action?type=design" class="new-btn">＋ 表紙新規登録</a>
      </div>

      <div class="design-list">

      	<c:forEach var="designGroup" items="${designGroupList}">
	        <div class="design-item">
	          <img src="${sessionScope.basePath}/goshuin_book_design/${designGroup.imagePath}" alt="${designGroup.name}">
	          <p>${designGroup.name}</p>
	        </div>
        </c:forEach>

      </div>

      <div class="register-header">
        <div class="register-title">ステッカーデザイン一覧</div>
        <a href="GoshuinBookDesignRegist.action?type=sticker" class="new-btn">＋ ステッカー新規登録</a>
      </div>

      <div class="design-list">
        <c:forEach var="sticker" items="${stickerList}">
	        <div class="design-item">
	          <img src="${sessionScope.basePath}/sticker/${sticker.imagePath}" alt="${sticker.name}">
	          <p>${sticker.name}</p>
	        </div>
        </c:forEach>

      </div>
	</c:param>
</c:import>