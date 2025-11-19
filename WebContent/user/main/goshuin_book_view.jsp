<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base.jsp">
    <c:param name="content">

        <h1 class="page-title">御朱印帳</h1>

        <%-- セッションのユーザーから現在の御朱印帳を取得する場合はこれがあってもOK --%>
        <%-- <c:set var="goshuinBook" value="${sessionScope.user.activeGoshuinBook}" /> --%>

        <c:if test="${empty goshuinBook}">
            <p>表示できる御朱印帳がありません。</p>
        </c:if>

        <c:if test="${not empty goshuinBook}">

            <div class="goshuin-gallery">
                <div class="gallery-track">

                    <c:if test="${empty goshuinBook.goshuinList}">
                        <p>まだ御朱印が登録されていません。</p>
                    </c:if>

                    <c:forEach var="owned" items="${goshuinBook.goshuinList}">
					    <c:if test="${not empty owned.goshuin and not empty owned.goshuin.imagePath}">
					        <img src="/goshuin/user/images/goshuin/${owned.goshuin.imagePath}"
					             alt="御朱印" class="goshuin-img">
					        <%-- 必要ならデバッグ用にパス表示 --
					        <p>DEBUG: ${owned.goshuin.imagePath}</p>
					        --%>
					    </c:if>
					</c:forEach>

                </div>
            </div>

            <div class="kasutamubtn-row">
                <a href="PastGoshuinBookList.action" class="nav-btn custom-left">過去一覧</a>
                <a href="GoshuinBookEdit.action?goshuinBookId=${goshuinBook.id}"
                   class="nav-btn custom-right">編集</a>
            </div>

        </c:if>

    </c:param>
</c:import>
