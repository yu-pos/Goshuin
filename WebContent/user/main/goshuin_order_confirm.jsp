<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base2.jsp">
	<c:param name="content">
		<h2>上杉神社 御朱印</h2>

        <div class="goshuin-image-area">
          <img src="${sessionScope.basePath}/goshuin/${regdGoshuin.imagePath}" alt="御朱印イメージ" class="goshuin-img">
        </div>

        <p class="confirm-message">
          以下の御朱印を購入します。<br>
          よろしければ「購入する」ボタンを押してください。
        </p>

        <div class="confirm-details">
          <p>価格：<strong>150円</strong></p>
          <p>支払い方法：ポイント支払い</p>
        </div>

        <div class="purchase-btn-area">
       		<form action="PaymentExecute.action" method="POST">
        		<input type="hidden" name="regdGoshuinId" value="${regdGoshuin.id}">
		        <input type="submit" class="purchase-btn" value="購入する">
	        </form>
	        <form action="GoshuinChoose.action" method="POST">
	        	<input type="hidden" name="shrineAndTempleId" value="${shrineAndTempleId}">
		        <input type="submit" class="cancel-btn" value="戻る">
        	</form>
        </div>
	</c:param>
</c:import>