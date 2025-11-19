<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<div class="page-header">
          <a href="name.html" class="back-btn">← <span>戻る</span></a>
          <div class="title-with-fav">
            <h1 class="page-title">${shrineAndTemple.name}</h1>
            <button id="fav-btn" class="fav-btn" aria-label="お気に入り">💖</button>
          </div>
        </div>


        <!-- 📸 神社画像 -->
        <img src="images/126.jpg" alt="上杉神社" class="temple-img-large">

        <!-- 📖 情報 -->
        <section class="temple-info">
          <p><strong>所在地：</strong>${shrineAndTemple.address }</p>
          <p><strong>ご利益：</strong>${shrineAndTemple.description}</p>
          <p><strong>タグ：</strong>
    <c:forEach var="tag" items="${shrineAndTemple.tagList}">
      <span class="tag">#${tag.name}</span>
    </c:forEach>
  </p>

          <p><strong>紹介：</strong>${shrineAndTemple.description}</p>
          <p><strong>周辺状況：</strong>${shrineAndTemple.areaInfo}</p>


        </section>

        <!-- 🗣️ 口コミセクション -->
       <section class="review-section">
		  <div class="review-header">
		    <h2>口コミ</h2>
		    <a href="kutikomi.html" class="tuokou-btn">＋ 投稿</a>
		  </div>

		  <div class="review-slider">

		  	<c:forEach var="review" items="${reviewList}">
			    <div class="review-item">
			      <img src="images/134.png" alt="ユーザー1" class="user-icon">
			      <div class="review-content">
			      <h4>${review.userName} さん</h4>
			       <p>${review.text}</p>
			        <button class="like-btn">♡ <span class="like-count">${review.likeCount}</span></button>
			      </div>
			    </div>

			</c:forEach>
		  </div>
		</section>

		<div id="like-message" class="like-message">いいねしました！</div>

		<img src="images/132.png" alt="上杉神社" class="temple-img-large">

	</c:param>
</c:import>