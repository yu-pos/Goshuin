<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h1>商品券の利用方法</h1>
        <p class="coupon-note">
          この画面を店員にお見せください。<br>
          使用後は「使用する」ボタンを押してください。
        </p>

        <!-- 🎟️ 商品券イメージ -->
        <div class="coupon-detail">
          <img src="images/coupon1.jpg" alt="商品券" class="coupon-detail-img">
          <h2>山形温泉利用券（500円）</h2>
          <p>利用期限：2025年12月31日</p>
        </div>

        <!-- 使用ボタン -->
        <div class="use-btn-area">
          <a href="" class="use-btn-large">使用する</a>
        </div>
	</c:param>
</c:import>