<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base2.jsp">
    <c:param name="content">
    <link rel="stylesheet" href="/goshuin/user/css/password_toggle.css?v=1" />
	<script src="/goshuin/user/scripts/password_toggle.js?v=1"></script>

        <div class="register">
            <h1>新規登録</h1>

            <!-- エラーメッセージ表示 -->
            <c:if test="${not empty errors}">
                <div class="error" style="color:red;">
                    <c:forEach var="e" items="${errors.values()}">
					    <p>${e}</p>
					</c:forEach>
                </div>
            </c:if>

            <form action="UserRegistExecute.action" method="POST">
			    <label for="userName">ユーザー名</label>
			    <input type="text" id="userName" name="userName"
			           value="${userName}" required>

			    <label for="realName">氏名</label>
			    <input type="text" id="realName" name="realName"
			           value="${realName}" required>

			    <label for="birthDate">生年月日</label>
			    <input type="date" id="birthDate" name="birthDate"
			           value="${birthDate}" required>

			    <label for="address">住所</label>
			    <input type="text" id="address" name="address"
			           value="${address}" required>

			    <label for="tel">電話番号</label>
			    <input type="text" id="tel" name="tel"
			           value="${tel}" required>

			    <label for="password">パスワード</label>
				<input type="password" id="password" name="password" required>

				<div class="password-row">
				  <label class="showpass">
				    <input type="checkbox" id="togglePassword">
				    パスワードを表示
				  </label>
				</div>

			    <input type="submit" value="登録">
			</form>


            <!-- ログイン画面へのリンク -->
            <a href="Login.action">ログインはこちら</a>
        </div>
    </c:param>
</c:import>
