<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<div class="card">
          <h3>アカウント詳細</h3>
          <div>ID：○○</div>
          <div>権限：運営者or管理者</div>
          <div>状態：有効or無効</div>
      	</div>
    	<a class="btn danger" href="#">無効化</a>
    	<!--アカウントがすでに無効だった場合<div class="muted">このアカウントは無効です。</div>と画面に表示する-->
    	<!--ログイン中のアカウントだった場合<div class="muted">※ 現在ログイン中のアカウントは無効化できません。
      	と表示する</div>-->
	</c:param>
</c:import>