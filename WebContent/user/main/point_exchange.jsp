<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<div class="point-header">
          <h1>ポイント交換</h1>
          <p class="user-point">所持ポイント：<span id="point-count">25</span> pt</p>
        </div>

        <!-- 🔸 表紙カテゴリ -->
        <section class="point-category">
          <h2>御朱印帳表紙</h2>
          <div class="scroll-container">
            <div class="point-card">
              <img src="images/129.png" alt="表紙1" class="reward-img">
              <div class="point-info">
                <h4>桜デザイン</h4>
                <p>必要：5pt</p>
                <a href="confirm-cover1.html" class="exchange-btn">交換</a>
              </div>
            </div>

            <div class="point-card">
              <img src="images/133.png" alt="表紙2" class="reward-img">
              <div class="point-info">
                <h4>和風金襴</h4>
                <p>必要：5pt</p>
                <a href="confirm-cover2.html" class="exchange-btn">交換</a>
              </div>
            </div>

            <div class="point-card">
              <img src="images/cover3.jpg" alt="表紙3" class="reward-img">
              <div class="point-info">
                <h4>藍染模様</h4>
                <p>必要：5pt</p>
                <a href="confirm-cover3.html" class="exchange-btn">交換</a>
              </div>
            </div>
          </div>
        </section>

        <!-- 🔸 ステッカーカテゴリ -->
        <section class="point-category">
          <h2>ステッカー</h2>
          <div class="scroll-container">
            <div class="point-card">
              <img src="images/130.png" alt="ステッカー1" class="reward-img">
              <div class="point-info">
                <h4>狐デザイン</h4>
                <p>必要：2pt</p>
                <a href="points2.html" class="exchange-btn">交換</a>
              </div>
            </div>

            <div class="point-card">
              <img src="images/133.jpg" alt="ステッカー2" class="reward-img">
              <div class="point-info">
                <h4>鶴ステッカー</h4>
                <p>必要：2pt</p>
                <a href="confirm-sticker2.html" class="exchange-btn">交換</a>
              </div>
            </div>

            <div class="point-card">
              <img src="images/sticker3.png" alt="ステッカー3" class="reward-img">
              <div class="point-info">
                <h4>上杉家紋</h4>
                <p>必要：2pt</p>
                <a href="confirm-sticker3.html" class="exchange-btn">交換</a>
              </div>
            </div>
          </div>
        </section>
	</c:param>
</c:import>