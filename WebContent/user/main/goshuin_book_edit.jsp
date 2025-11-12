<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<div class="page-header">
            <h1 class="page-title">御朱印帳<br>編集</h1>
            <a href="custom2.html" class="save-btn">💾完了</a>
        </div>

        <!-- 📘 表紙（ステッカー貼付けエリア） -->
        <div class="goshuin-cover">
          <img src="images/129.png" alt="御朱印帳の表紙" class="cover-img">
          <div class="sticker-layer" id="sticker-layer"></div>
        </div>

        <!-- 🩵 ステッカーメニュー（横スクロール） -->
        <div class="sticker-menu">
          <img src="images/129.png" class="sticker-option" alt="ステッカー1">
        </div>
        <div class="sticker-menu">
          <img src="images/130.png" class="sticker-option" alt="ステッカー1">
        </div>
	</c:param>
</c:import>