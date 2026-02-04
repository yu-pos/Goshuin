<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<div class="omikuji-box">
          <h2 class="omikuji-result">${result.key}</h2>
		  <p class="omikuji-message">${result.value}</p>
        </div>

        <a href="Main.action" class="close-btn">閉じる</a>

	</c:param>
</c:import>