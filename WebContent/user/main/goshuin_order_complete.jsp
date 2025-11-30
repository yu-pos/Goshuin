<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base2.jsp">
	<c:param name="content">
		<h1>購入完了</h1>

        <!-- メッセージ表示 -->
        <c:if test="${not empty messages}">
             <div class="purchase-btn-area">
                 <c:forEach var="m" items="${messages.values()}">
		    <p>${m}</p>
		</c:forEach>
             </div>
        </c:if>

        <a href="Main.action">メイン画面へ</a>

	</c:param>
</c:import>