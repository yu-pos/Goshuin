<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">

		<!-- 仮置き -->
		<form action="GoshuinChoose.action" method="POST">
			<input type="hidden" name="shrineAndTempleId" value="1">
			<input class="purchase-btn" type="submit" value="選択画面へ"/>
		</form>
	</c:param>
</c:import>