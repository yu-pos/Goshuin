<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base.jsp">
    <c:param name="content">
        <div class="card">
            <h3>アカウントを無効化しますか？</h3>
            <p>ID: ${targetOperator.id} を無効化します</p>
            <div class="row">
                <!-- 無効化実行へ遷移するボタン -->
                <form action="OperatorDisableExecute.action" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="${targetOperator.id}">
                    <button id="confirmBtn" class="btn danger" type="submit">無効化する</button>
                </form>
                <!-- キャンセルは一覧へ戻る -->
                <a class="btn secondary" href="OperatorAccountList.action">キャンセル</a>
            </div>
        </div>

        <!-- すでに無効な場合の注意表示 -->
        <c:if test="${!targetOperator.enable}">
            <div class="muted">このアカウントはすでに無効です。</div>
        </c:if>

        <!-- ログイン中のアカウントの場合の注意表示 -->
        <c:if test="${sessionScope.operator.id == targetOperator.id}">
            <div class="muted">※ 現在ログイン中のアカウントは無効化できません。</div>
        </c:if>
    </c:param>
</c:import>