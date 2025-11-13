<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h1>商品券一覧</h1>
        <p class="note">お手持ちの商品券を確認できます。</p>
        <section class="coupon-list">
          <!-- 🧧 商品券カード1 -->
          <div class="coupon-card">
            <img src="images/135.png" alt="商品券1" class="coupon-img">
            <div class="coupon-info">
              <h3>山形商品券（500円）</h3>
              <p>利用期限：2025年12月31日</p>
              <a href="voucherUseAction" class="nav-btn">使用する</a>
            </div>
          </div>
        </section>
	</c:param>
</c:import>