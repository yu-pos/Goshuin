<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base2.jsp">
    <c:param name="content">
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
                       value="${tel}">

                <label for="password">パスワード</label>
                <!-- 本当は type="password" がオススメ -->
                <input type="password" id="password" name="password"
                       placeholder="パスワードを入力">
                <input type="submit" value="ログイン">
            </form>
        </div>
        <a href="sinki.html">新規登録はこちら</a>
    </c:param>
</c:import>
