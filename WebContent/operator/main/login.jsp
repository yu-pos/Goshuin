<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:import url="../base2.jsp">
    <c:param name="content">
        <div class="card">
            <h3>ログイン</h3>


            <!-- エラーメッセージ表示 -->
            <c:if test="${not empty errors}">
    			<div class="error">
        			<c:forEach var="err" items="${errors}">
            			<p>${err}</p>
        			</c:forEach>
   				</div>
			</c:if>


            <!-- ログインフォーム -->
            <form id="loginForm" action="LoginExecute.action" method="post">
                <label>ID</label>

                <input id="loginId" name="id" type="text" value="${id}" required>


                <label>パスワード</label>
                <input id="loginPassword" name="password" type="password" required>


                <div>
                    <button class="btn primary" type="submit">ログイン</button>
                </div>
            </form>
        </div>
    </c:param>
</c:import>