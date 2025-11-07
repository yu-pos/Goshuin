<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="content">
  <!-- ここが main の中身として base.jsp に差し込まれます -->
  <div class="login">
    <h1>ログイン画面</h1>

    <label for="tell">電話番号</label>
    <input type="text" id="tell" placeholder="電話番号を入力" />

    <label for="password">パスワード</label>
    <!-- パスワードは適切に type="password" にしておきます -->
    <input type="password" id="password" placeholder="パスワードを入力" />

    <!-- 元HTMLの動作を踏襲：リンクでメニューへ遷移 -->
    <!-- サーブレットで認証する場合は form + action に切り替えてください -->
    <a href="menu.jsp" class="login-btn">ログイン</a>
  </div>

  <a href="sinki.jsp">新規登録はこちら</a>
</c:set>

<jsp:include page="base.jsp">
  <jsp:param name="content" value="${content}"/>
</jsp:include>
