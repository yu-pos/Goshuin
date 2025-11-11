<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base2.jsp">
	<c:param name="content">
		<h1>新規登録</h1>
	    <form>
	        <table>
	            <tr>
	                <td>ユーザー名</td>
	                <td><input type="text"></td>
	            </tr>
	            <tr>
	                <td>氏名</td>
	                <td><input type="text"></td>
	            </tr>
	            <tr>
	                <td>生年月日</td>
	                <td><input type="text"></td>
	            </tr>
	            <tr>
	                <td>住所</td>
	                <td><input type="text"></td>
	            </tr>
	            <tr>
	                <td>電話番号</td>
	                <td><input type="text"></td>
	            </tr>
	            <tr>
	                <td>パスワード</td>
	                <td><input type="text"></td>
	            </tr>
	        </table>
	        <a href="sinki2.html" class="sinki-btn">登録</a>
	     </form>
	</c:param>
</c:import>