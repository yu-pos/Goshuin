<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base2.jsp">
    <c:param name="content">
        <div class="card">
            <h3>パスワードを変更しました</h3>
            <p class="muted">ログイン状態は維持されています。引き続きご利用ください。</p>
            <div class="row">
                <!-- メイン画面へ遷移するリンク -->
                <a class="btn primary" href="main.jsp">メインへ</a>
            </div>
        </div>
    </c:param>
</c:import>