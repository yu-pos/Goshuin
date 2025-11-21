<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		    <div class="card">
      <h3>新規アカウント発行</h3>
      <div class="row">
        <label>権限を選択</label>
        <div>
          <label style="margin-right:16px;">
            <input type="radio" name="role" value="admin"> 管理者
          </label>
          <label>
            <input type="radio" name="role" value="operator"> 運営者
          </label>
        </div>
      </div>
      <div class="row">
        <button id="issueBtn" class="btn primary" type="button">アカウント発行</button>
      </div>
       <!--アカウント発行ボタンを押すと下記の情報が表示される-->
      <div class="card">
            <h3>アカウント発行完了</h3>
            <p>ID：<strong>○○</strong></p>
            <p>初期PW：<strong>○○○○</strong></p>
            <p class="muted">※ 初回ログイン時は上記の初期PWを入力すると、パスワード変更画面に遷移します。</p>
      </div>
    </div>
	</c:param>
</c:import>