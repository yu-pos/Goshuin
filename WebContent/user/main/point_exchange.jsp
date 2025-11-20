<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<div class="point-header">
          <h1>ポイント交換</h1>
          <p class="user-point">所持ポイント：<span id="point-count">${point}</span> pt</p>
        </div>

        <!-- 🔸 表紙カテゴリ -->
        <section class="point-category">
          <h2>御朱印帳表紙</h2>
          <div class="scroll-container">
          	<c:forEach var="regdGoshuinBookDesignGroup" items="${regdGoshuinBookDesignGroupList}">
	            <div class="point-card">
	              <img src="images/129.png" alt="表紙1" class="reward-img">
	              <div class="point-info">
	                <h4>${regdGoshuinBookDesignGroup.name}</h4>
	                <p>必要：5pt</p>
	                <c:choose>
	                	<c:when test="${regdGoshuinBookDesignGroup.isOwned()}">
	                		<div><font color="red">既に購入済みのデザインです</font></div>
	                	</c:when>
	                	<c:when test="${5 > point}">
							<div><font color="red">ポイントが不足しています</font></div>
	                	</c:when>
	                	<c:otherwise>
	                		<form action="PointExchangeExecute.action" method="GET">
	                			<input type="hidden" name="regdGoshuinBookDesignGroupId" value="${regdGoshuinBookDesignGroup.id}">
	                			<input type="submit" value="交換" class="exchange-btn">
	                		</form>
	                	</c:otherwise>

	                </c:choose>
	              </div>
	            </div>
            </c:forEach>

          </div>
        </section>

        <!-- 🔸 ステッカーカテゴリ -->
        <section class="point-category">
          <h2>ステッカー</h2>
          <div class="scroll-container">
          	<c:forEach var="regdGoshuinBookSticker" items="${regdGoshuinBookStickerList}">
	            <div class="point-card">
	              <img src="images/130.png" alt="ステッカー1" class="reward-img">
	              <div class="point-info">
	                <h4>${regdGoshuinBookSticker.name}</h4>
	                <p>必要：2pt</p>
	                <form action="PointExchangeExecute.action" method="GET">
	                	<input type="hidden" name="regdGoshuinBookStickerId" value="${regdGoshuinBookSticker.id}">
	                	<input type="submit" value="交換" class="exchange-btn">
	                </form>
	              </div>
	            </div>
            </c:forEach>


          </div>
        </section>
	</c:param>
</c:import>