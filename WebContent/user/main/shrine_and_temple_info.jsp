<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<a href="ShrineAndTempleSearch.action" class="back-btn">← <span>戻る</span></a>
		<br>
		<br>
		<div>
			<c:if test="${favoriteSuccess}">
		  		神社仏閣をお気に入り登録しました！
			</c:if>
		</div>
		<div class="page-header">


          <div class="title-with-fav">
            <h1 class="page-title">${shrineAndTemple.name}</h1>
            <div class="like-btn">
           	<c:choose>
           	<c:when test="${isFavorited}">
	            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" stroke="#ff584d" fill="#ff584d" viewBox="0 0 256 220"><path d="M223,57a58.07,58.07,0,0,0-81.92-.1L128,69.05,114.91,56.86A58,58,0,0,0,33,139l89.35,90.66a8,8,0,0,0,11.4,0L223,139a58,58,0,0,0,0-82Zm-11.35,70.76L128,212.6,44.3,127.68a42,42,0,0,1,59.4-59.4l.2.2,18.65,17.35a8,8,0,0,0,10.9,0L152.1,68.48l.2-.2a42,42,0,1,1,59.36,59.44Z"></path></svg>
	            登録済
           	</c:when>
           	<c:otherwise>
	            <a href="FavoriteRegistExecute.action?id=${shrineAndTemple.id}" id="fav-btn" class="fav-btn" aria-label="お気に入り">
	            	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#000000" viewBox="0 0 256 220"><path d="M223,57a58.07,58.07,0,0,0-81.92-.1L128,69.05,114.91,56.86A58,58,0,0,0,33,139l89.35,90.66a8,8,0,0,0,11.4,0L223,139a58,58,0,0,0,0-82Zm-11.35,70.76L128,212.6,44.3,127.68a42,42,0,0,1,59.4-59.4l.2.2,18.65,17.35a8,8,0,0,0,10.9,0L152.1,68.48l.2-.2a42,42,0,1,1,59.36,59.44Z"></path></svg>
	            	登録
	            </a>
	        </c:otherwise>
            </c:choose>
            </div>
          </div>
        </div>


        <!-- 📸 神社画像 -->
        <img src="${sessionScope.basePath}/shrine_and_temple/${shrineAndTemple.imagePath}" alt="${shrineAndTemple.name}" class="temple-img-large">

        <!-- 📖 情報 -->
        <section class="temple-info">
          <p><strong>所在地：</strong>${shrineAndTemple.address }</p>
          <p><strong>タグ：</strong>
		    <c:forEach var="tag" items="${shrineAndTemple.tagList}">
		      <span class="tag">#${tag.name}</span>
		    </c:forEach>
  		  </p>

          <p><strong>紹介：</strong>${shrineAndTemple.description}</p>
          <p><strong>周辺情報：</strong>${shrineAndTemple.areaInfo}</p>


        </section>

        <!-- 🗣️ 口コミセクション -->
       <section class="review-section">
		  <div class="review-header">
		    <h2>口コミ</h2>
		    <a href="ReviewInput.action?id=${shrineAndTemple.id}" class="tuokou-btn">＋ 投稿</a>
		  </div>

		  <div class="review-slider">

		  	<c:forEach var="review" items="${reviewList}">
			    <div class="review-item">
			      <img src="${sessionScope.basePath}/user_image/${review.userImagePath}" alt="${review.userName}" class="user-icon">
			      <div class="review-content">
			      <h4>${review.userName} さん</h4>
			      	<c:if test="${review.imagePath != null}">
			      		<img src="${sessionScope.basePath}/review/${review.imagePath}" alt="画像" class="event-img">
			      	</c:if>
			       <p>${review.text}</p>
			        <button class="like-btn ${review.liked ? 'liked' : ''}" data-review-id="${review.id}">
        				♡ <span class="like-count">${review.likeCount}</span>
        			</button>
			      </div>
			    </div>

			</c:forEach>
		  </div>
		</section>

		<div id="like-message" class="like-message">いいねしました！</div>

		<section class="temple-info">
		<h2>マップ</h2>
		<iframe src="${shrineAndTemple.mapLink}" width="600" height="450" style="border:0; width: 100%;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
		</section>
	</c:param>
</c:import>