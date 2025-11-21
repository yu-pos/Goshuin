<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base2.jsp">
	<c:param name="content">
		<div class="card">
      		<h3>ログイン</h3>
      		<form id="loginForm">
        		<label>ID</label>
        		<input id="loginId" type="text" required>
        		<label>パスワード</label>
        		<input id="loginPassword" type="password" required>
        		<div>
          			<a href="main.html" class="btn primary" type="submit">ログイン</a>
        		</div>
      		</form>
    	</div>
	</c:param>
</c:import>>