<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base2.jsp">
	<c:param name="content">
		<h1>御朱印購入</h1>
        <h2>上杉神社 御朱印</h2>

        <!-- 御朱印画像 -->
        <div class="goshuin-image-area">
          <img src="images/128.jpg" alt="上杉神社の御朱印" class="goshuin-img">
        </div>

        <!-- 説明文 -->
        <p class="goshuin-desc">
          上杉謙信公を祀る上杉神社の御朱印です。<br>
          勝負運・学業成就のご利益があるとされています。
        </p>

        <!-- 購入ボタン -->
        <div class="purchase-btn-area">
          <a href="kounyu2.html" class="purchase-btn">購入する</a>
        </div>
        <div class="goshuin-image-area">
          <img src="images/127.jpg" alt="上杉神社の御朱印" class="goshuin-img">
        </div>

        <!-- 説明文 -->
        <p class="goshuin-desc">
          上杉謙信公を祀る上杉神社の御朱印です。<br>
          勝負運・学業成就のご利益があるとされています。
        </p>

        <!-- 購入ボタン -->
        <div class="purchase-btn-area">
          <a href="purcha.html" class="purchase-btn">購入する</a>
        </div>
	</c:param>
</c:import>