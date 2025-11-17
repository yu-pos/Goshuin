<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base2.jsp">
	<c:param name="content">
		<div class="login">
	        <h1>ログイン画面</h1>
	        <label for="productNo">電話番号</label>
	        <input type="text" id="tel" placeholder="電話番号を入力">
	        <label for="productNo">パスワード</label>
	        <input type="text" id="password" placeholder="パスワードを入力">
	        <a href="menu.html" class="login-btn">ログイン</a>
	        </div>
        <a href="sinki.html">新規登録はこちら</a>
	</c:param>
</c:import>