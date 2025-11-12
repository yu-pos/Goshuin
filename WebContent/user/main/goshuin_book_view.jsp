<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		 <h1 class="page-title">御朱印帳</h1>

            <!-- 🖼️ スワイプギャラリー -->
            <div class="goshuin-gallery">
                <div class="gallery-track">
                    <img src="images/129.jpg" alt="御朱印1">
                    <img src="images/128.jpg" alt="御朱印2">
                    <img src="images/127.jpg" alt="御朱印3">
                </div>
            </div>

            <!-- 🟩 カスタムボタン（画像の下に横並び） -->
            <div class="kasutamubtn-row">
                <a href="view.html" class="nav-btn custom-left">一覧</a>
                <a href="custom.html" class="nav-btn custom-right">編集</a>
            </div>
	</c:param>
</c:import>