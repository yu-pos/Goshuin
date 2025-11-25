<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base2.jsp">
    <c:param name="content">
        <div class="card" style="max-width:520px;">
            <h3>パスワード変更</h3>

            <!-- エラーメッセージ表示 -->
            <c:if test="${not empty errors}">
                <div class="error">
                    <ul>
                        <c:forEach var="err" items="${errors}">
                            <li>${err}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <!-- 対象ユーザー表示 -->
            <p id="targetUser" class="row">
                対象アカウントID：<c:out value="${operator.id}" default="未指定"/>
            </p>

            <!-- パスワード変更フォーム -->
            <form id="pwForm" action="PasswordChangeExecute.action" method="post">
                <label>新しいパスワード</label>
                <input id="p1" name="newPassword" type="password" minlength="8" required>

                <label>新しいパスワード（確認）</label>
                <input id="p2" name="newPasswordConfirm" type="password" minlength="8" required>

                <div class="row">
                    <button class="btn primary" type="submit">変更して進む</button>
                </div>
            </form>
        </div>
    </c:param>
</c:import>