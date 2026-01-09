<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<div class="point-confirm">
          <h1>ポイント交換確認</h1>
          <p>以下のアイテムを本当に交換しますか？</p>
          <!-- 交換対象アイテム表示 -->
          <c:choose>
          <c:when test="${type=='design'}">
	          <div class="confirm-item">
	            <img src="${sessionScope.basePath}/goshuin_book_design/${designGroup.imagePath}" alt="御朱印帳表紙" class="confirm-img">
	            <h3>${designGroup.name}</h3>
	            <p>必要ポイント：<span class="need-point">5 pt</span></p>
	          </div>
          	  <!-- ボタン配置 -->
	          <form action="PointExchangeExecute.action" method="POST" class="confirm-buttons">
	          	<input type="hidden" name="type" value="design">
	          	<input type="hidden" name="designGroupId" value="${designGroup.id}">
	            <a href="PointExchange.action" class="btn back-btn">戻る</a>
	            <input type="submit" value="交換する" class="btn exchange-btn">
	          </form>

          </c:when>
	          <c:when test="${type=='sticker'}">
	          <div class="confirm-item">
	            <img src="${sessionScope.basePath}/sticker/${sticker.imagePath}" alt="御朱印帳表紙" class="confirm-img">
	            <h3>${sticker.name}</h3>
	            <p>必要ポイント：<span class="need-point">2 pt</span></p>
	          </div>
	          <!-- ボタン配置 -->
	          <form action="PointExchangeExecute.action" method="POST" class="confirm-buttons">
	          	<input type="hidden" name="type" value="sticker">
	          	<input type="hidden" name="stickerId" value="${sticker.id}">
	            <a href="PointExchange.action" class="btn back-btn">戻る</a>
	            <input type="submit" value="交換する" class="btn exchange-btn">
	          </form>
          </c:when>
          </c:choose>


        </div>
	</c:param>
</c:import>