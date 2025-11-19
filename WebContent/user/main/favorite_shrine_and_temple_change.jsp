<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h1>お気に入り入れ替え</h1>
        <p>現在3つ登録されています。どれかと入れ替えますか？</p>

        <div class="favorite-list">
          <div class="favorite-item">
            <img src="images/125.jpg" alt="立石寺" class="favorite-img">
            <p class="favorite-name">${shrineAndTemple.id }</p>
            <button class="replace-btn" data-id="1">入れ替える</button>
          </div>

          <div class="favorite-item">
            <img src="images/126.jpg" alt="上杉神社" class="favorite-img">
            <p class="favorite-name">${shrineAndTemple.id }</p>
            <button class="replace-btn" data-id="2">入れ替える</button>
          </div>

          <div class="favorite-item">
            <img src="images/124.jpg" alt="若松寺" class="favorite-img">
            <p class="favorite-name">${shrineAndTemple.id }</p>
            <button class="replace-btn" data-id="3">入れ替える</button>
          </div>
        </div>

        <div class="new-favorite">
          <h3>新しく登録する神社</h3>
          <img src="images/124.jpeg" alt="新神社" class="new-img">
          <p class="new-name">${shrineAndTemple.name}</p>
        </div>

        <div class="btn-area">
          <a href="temple-detail.html" class="cancel-btn">キャンセル</a>
        </div>
	</c:param>
</c:import>
