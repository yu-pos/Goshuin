<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		 <div class="profile-header">
          <h1 class="page-title">○○さんのプロフィール</h1>
          <a href="profile2.html" class="edit-btn" aria-label="プロフィール編集">✏️ 編集</a>
        </div>

        <!-- 👤 ユーザー情報 -->
        <section class="profile-section">
          <img src="images/134.png" alt="プロフィール写真" class="profile-img">
          <div class="profile-info">
            <h2 class="username">山形太郎</h2>
            <p class="rank">
              <img src="images/123.png" alt="ランクアイコン" class="rank-icon">
            </p>
          </div>
        </section>

        <!-- 💖 お気に入り神社仏閣 -->
       <section class="favorite-section">
          <h3>お気に入りの神社・仏閣</h3>
          <div class="favorite-slider">

            <a href="tera-uesugi.html" class="favorite-item">
              <img src="images/126.jpg" alt="上杉神社">
            <p>上杉神社</p>
            </a>

            <a href="tera-rissyaku.html" class="favorite-item">
              <img src="images/tera1.jpg" alt="立石寺">
              <p>立石寺</p>
            </a>

            <a href="tera-kumano.html" class="favorite-item">
              <img src="images/tera2.jpg" alt="熊野大社">
              <p>熊野大社</p>
            </a>
          </div>
        </section>

        <!-- 📖 My御朱印帳 -->
        <section class="goshuin-section">
          <h3>My御朱印帳</h3>
          <a href="goshuin.html" class="goshuin-book">
            <img src="images/129.png" alt="御朱印帳の表紙" class="goshuin-img">
          </a>
        </section>

	</c:param>
</c:import>