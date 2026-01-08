<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base2.jsp">
    <c:param name="content">
    <link rel="stylesheet" href="/goshuin/user/css/password_toggle.css?v=1" />
	<script src="/goshuin/user/scripts/password_toggle.js?v=1"></script>

        <div class="login">
            <h1>ログイン画面</h1>

            <!-- 🔻ここでエラーを表示する -->
            <c:if test="${not empty errors}">
                <div class="error" style="color:red;">
                    <c:forEach var="e" items="${errors}">
                        <p>${e}</p>
                    </c:forEach>
                </div>
            </c:if>

            <form action="LoginExecute.action" method="POST">
                <label for="tel">電話番号</label>
                <!-- 入力値を戻したい場合 -->
                <input type="text" id="tel" name="tel"
                       placeholder="電話番号を入力"
                       value="${tel}" required>

                <label for="password">パスワード</label>
				<input type="password" id="password" name="password"
				       placeholder="パスワードを入力" required>

				<label class="showpass">
				  <input type="checkbox" id="togglePassword">
				  パスワードを表示
				</label>

				<input type="submit" value="ログイン">
            </form>
        </div>
        <a href="UserRegist.action">新規登録はこちら</a>
    </c:param>
</c:import>
