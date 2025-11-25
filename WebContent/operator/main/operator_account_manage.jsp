<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base.jsp">
    <c:param name="content">
        <div class="card">
            <h3>アカウント詳細</h3>
            <div>ID：${operator.id}</div>
            <div>
                権限：
                <c:choose>
                    <c:when test="${operator.admin}">管理者</c:when>
                    <c:otherwise>運営者</c:otherwise>
                </c:choose>
            </div>
            <div>
                状態：
                <c:choose>
                    <c:when test="${operator.enable}">有効</c:when>
                    <c:otherwise>無効</c:otherwise>
                </c:choose>
            </div>
        </div>

        <!-- 無効化ボタン（有効な場合のみ表示） -->
        <c:if test="${operator.enable}">
            <a class="btn danger" href="OperatorDisableConfirm.action?id=${operator.id}">無効化</a>
        </c:if>

        <!-- アカウントがすでに無効だった場合 -->
        <c:if test="${!operator.enable}">
            <div class="muted">このアカウントは無効です。</div>
        </c:if>

        <!-- ログイン中のアカウントだった場合 -->
        <c:if test="${sessionScope.operator.id == operator.id}">
            <div class="muted">※ 現在ログイン中のアカウントは無効化できません。</div>
        </c:if>
    </c:param>
</c:import>