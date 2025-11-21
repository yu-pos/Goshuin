<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<div class="card">
      		<h3>アカウントを無効化しますか？</h3>
      		<p>ID: ○○を無効化します</p>
      		<div class="row">
        		<button id="confirmBtn" class="btn danger">無効化する</button>
        	<a class="btn secondary" href="operators.html">キャンセル</a>
      		</div>
    	</div>
	</c:param>
</c:import>