<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		 <div class="profile-header">
		  <c:choose>
		  <c:when test="${selectedUser.id == sessionScope.user.id}">
		  	<h1 class="page-title">プロフィール</h1>
          	<a href="ProfileEdit.action" class="edit-btn" aria-label="プロフィール編集">✏️ 編集</a>
		  </c:when>
		  <c:otherwise>
		  	<h1 class="page-title">${selectedUser.userName}さんのプロフィール</h1>
		  </c:otherwise>
		  </c:choose>

        </div>

        <!-- 👤 ユーザー情報 -->
        <section class="profile-section">
          <img src="${sessionScope.basePath}/profile/${selectedUser.profileImagePath}" alt="プロフィール写真" class="profile-img">
          <div class="profile-info">
            <h2 class="username">${selectedUser.userName}</h2>
            <p class="rank">
              <img src="/goshuin/user/images/${rank.imagePath}" alt="ランクアイコン" class="rank-icon">
            </p>
          </div>
        </section>

        <!-- 💖 お気に入り神社仏閣 -->
       <section class="favorite-section">
          <h3>お気に入りの神社・仏閣</h3>
          <div class="favorite-slider">

			<c:forEach var="shrineAndTemple" items="${shrineAndTempleList}">
				<a href="ShrineAndTempleInfo.action?id=${shrineAndTemple.id}" class="favorite-item">
              		<img src="${sessionScope.basePath}/shrine_and_temple/${shrineAndTemple.imagePath}" alt="${shrineAndTemple.name}">
            		<p>${shrineAndTemple.name}</p>
            	</a>
			</c:forEach>

          </div>
        </section>

		<c:if test="${selectedUser.id == sessionScope.user.id or selectedUser.isMyGoshuinBookPublic()}">
	        <!-- 📖 My御朱印帳 -->
	        <section class="goshuin-section">
	          <h3>My御朱印帳</h3>
	            <div class="goshuin-gallery">
	                <div class="gallery-track">
		                <div class="slide">
					      <div class="book-cover">
					        <img
					          src="/goshuin/saved_images/goshuin_book_design/${selectedUser.myGoshuinBook.goshuinBookDesign.imagePath}"
					          alt="${selectedUser.myGoshuinBook.goshuinBookDesign.name}"
					          class="book-cover-img" />

					        <c:forEach var="att" items="${selectedUser.myGoshuinBook.attachedStickerList}">
					          <img
					            src="/goshuin/saved_images/sticker/${att.goshuinBookSticker.imagePath}"
					            alt="${att.goshuinBookSticker.name}"
					            class="book-sticker"
					            style="left:${att.xPos}%; top:${att.yPos}%;"
					          />
					        </c:forEach>
					      </div>
					    </div>

                	<c:forEach var="goshuin" items="${selectedUser.myGoshuinBook.goshuinList}">
              			<div class="slide"><img src="${sessionScope.basePath}/goshuin/${goshuin.goshuin.imagePath}" alt="御朱印"></div>
                	</c:forEach>

	                </div>
	           	</div>
	        </section>
        </c:if>

	</c:param>
</c:import>