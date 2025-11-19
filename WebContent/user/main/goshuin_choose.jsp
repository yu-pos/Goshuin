<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base2.jsp">
	<c:param name="content">
		<h1>御朱印購入</h1>
        <h2>${shrineAndTempleName} 御朱印</h2>

		<c:forEach var="regdGoshuin" items="${regdGoshuinList}">

	        <!-- 御朱印画像 -->
	        <div class="goshuin-image-area">
	          <img src="/goshuin/user/images/goshuin/${regdGoshuin.imagePath}" alt="御朱印イメージ" class="goshuin-img">
	        </div>

				<!-- 販売期間 -->
				<p>
		        	販売期間:
		        	<c:if test="${regdGoshuin.saleStartDate != null}">
		        		${regdGoshuin.saleStartDate} ～ ${regdGoshuin.saleEndDate}
		        	</c:if>
		        	<c:if test="${regdGoshuin.saleStartDate == null}">
		        		常時
		        	</c:if>
		        </p>

	        <!-- 説明文 -->
	        <p class="goshuin-desc">
	          ${regdGoshuin.description}
	        </p>


			<%-- 購入済みだった場合 --%>
			<c:if test="${regdGoshuin.isOwned()}">
				<div><font color="red">既に購入済みの御朱印です</font></div>
			</c:if>

			<%-- 販売期間外だった場合 --%>
			<c:if test="${!regdGoshuin.isAvailable() and !regdGoshuin.isOwned()}">
				<div><font color="red">御朱印の販売期間外です</font></div>
			</c:if>

	        <!-- 購入ボタン -->
	        <c:if test="${!regdGoshuin.isOwned() and regdGoshuin.isAvailable()}">
		        <form class="purchase-btn-area" action="GoshuinOrderConfirm.action" method="POST">
		        	<input type="hidden" name="regdGoshuinId" value="${regdGoshuin.id}">
					<input type="hidden" name="shrineAndTempleId" value="${shrineAndTempleId}">
			        <input class="purchase-btn" type="submit" value="購入する">

		        </form>
	        </c:if>


		</c:forEach>
	</c:param>
</c:import>