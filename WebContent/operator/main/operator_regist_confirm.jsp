<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base.jsp">
    <c:param name="content">
        <div class="card">
            <h3>新規アカウント発行</h3>
            <form action="OperatorRegistExecute.action" method="post">
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
                    <button id="issueBtn" class="btn primary" type="submit">アカウント発行</button>
                </div>
            </form>

            <!-- 発行完了時のメッセージ表示 -->
            <c:if test="${not empty operatorId}">
                <div class="card">
                    <h3>アカウント発行完了</h3>
                    <p>ID：<strong>${operatorId}</strong></p>
                    <p>初期PW：<strong>${operatorPassword}</strong></p>
                    <p class="muted">
                        ※ 初回ログイン時は上記の初期PWを入力すると、パスワード変更画面に遷移します。
                    </p>
                </div>
            </c:if>
        </div>
    </c:param>
</c:import>