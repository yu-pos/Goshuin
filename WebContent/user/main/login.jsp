<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base2.jsp">
    <c:param name="content">
    <link rel="stylesheet" href="/goshuin/user/css/password_toggle.css?v=1" />
    <link rel="stylesheet" href="/goshuin/user/css/login.css?v=999">

	<script src="/goshuin/user/scripts/password_toggle.js?v=1"></script>
	<script src="/goshuin/user/scripts/phone_numeric_only.js"></script>

        <div>
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
            <h1>ログイン画面</h1>


                <label for="tel">電話番号</label>
				<input
				  type="tel"
				  id="tel"
				  name="tel"
				  value="${tel}"
				  pattern="[0-9]*"
				  inputmode="numeric"
				  maxlength="11"
				  placeholder="半角数字で入力してください"
				  required>

                <label for="password">パスワード</label>
				<input type="password" id="password" name="password"
				       placeholder="パスワードを入力してください" required>

				 <div class="password-row">
		          <label class="showpass">
		            パスワードを表示<input type="checkbox" id="togglePassword">
		          </label>
		        </div>

				<input type="submit" value="ログイン">
				<a href="UserRegist.action">新規登録はこちら</a>
            </form>
        </div>
    </c:param>
</c:import>
