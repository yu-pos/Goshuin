<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h1>お気に入り入れ替え</h1>
        <p>現在3つ登録されています。どれかと入れ替えますか？</p>

       <div class="favorite-list">
  <c:forEach var="favorite" items="${favoriteList}" varStatus="status">
    <div class="favorite-item">
      <img src="${sessionScope.basePath}/shrine_and_temple/${favorite.imagePath}" alt="${favorite.name}" class="favorite-img">
      <p class="favorite-name">${favorite.name}</p>

      <form action="FavoriteShrineAndTempleDeleteExecute.action" method="post">
        <input type="hidden" name="oldFavoriteShrineId" value="${favorite.id}" />
        <input type="hidden" name="newFavoriteShrineId" value="${shrineAndTemple.id}" />
        <button type="submit" class="replace-btn">入れ替える</button>
      </form>
    </div>
  </c:forEach>
</div>




        <div class="new-favorite">
          <h3>新しく登録する神社</h3>
          <img src="${sessionScope.basePath}/shrine_and_temple/${shrineAndTemple.imagePath}" alt="新神社" class="new-img" style="width: 100%;
  border-radius: 10px 10px 0 0;">
          <p class="new-name">${shrineAndTemple.name}</p>
        </div>

        <div class="btn-area">
          <a href="Main.action">キャンセル</a>
        </div>
	</c:param>
</c:import>
