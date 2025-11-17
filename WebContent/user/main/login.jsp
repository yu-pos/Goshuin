<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base2.jsp">
	<c:param name="content">
		<div class="login">
	        <h1>ログイン画面</h1>
	        <form action="LoginExecute.action" method="POST">
	        <label for="productNo">電話番号</label>
	        <input type="text" id="tel" name="tel" placeholder="電話番号を入力">
	        <label for="productNo">パスワード</label>
	        <input type="text" id="password" name="password" placeholder="パスワードを入力">
	        <input type="submit" value="ログイン">
	        </form>
	        </div>
        <a href="sinki.html">新規登録はこちら</a>
	</c:param>
</c:import>