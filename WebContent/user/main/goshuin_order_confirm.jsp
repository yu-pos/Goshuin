<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base2.jsp">
	<c:param name="content">
		<h2>上杉神社 御朱印</h2>

        <div class="goshuin-image-area">
          <img src="images/127.jpg" alt="上杉神社の御朱印" class="goshuin-img">
        </div>

        <p class="confirm-message">
          以下の御朱印を購入します。<br>
          よろしければ「購入する」ボタンを押してください。
        </p>

        <div class="confirm-details">
          <p>価格：<strong>150円</strong></p>
          <p>支払い方法：ポイント支払い</p>
        </div>

        <div class="purchase-btn-area">
          <a href="kounyu3.html" class="purchase-btn">購入する</a>
          <a href="kounyu.html" class="cancel-btn">戻る</a>
        </div>
	</c:param>
</c:import>