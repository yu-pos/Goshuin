<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h2>プロフィールを編集</h2>



        <!-- 🧾 入力フォーム -->
        <form action="ProfileUpdateExecute.action" method="POST" id="profileForm"  enctype="multipart/form-data">

           <!-- 👤 プロフィール画像変更 -->
	        <div class="edit-profile-img">
	          <img id="preview" src="${sessionScope.basePath}/profile/${sessionScope.user.profileImagePath}" alt="プロフィール画像" class="profile-img">
	          <label for="profileImage" class="change-btn">画像を変更</label>
	          <input type="file" name="image" id="profileImage" accept="image/*" hidden>
	        </div>


          <div class="form-group">
            <label for="username">ユーザー名</label>
            <input type="text" name="userName" id="username" value="${sessionScope.user.userName}">
          </div>


          <div class="form-group">
            <label>My御朱印帳</label>
            <div class="my-goshuin-area">
                <div class="book-cover">
					        <img
					          src="${sessionScope.basePath}/goshuin_book_design/${sessionScope.user.myGoshuinBook.goshuinBookDesign.imagePath}"
					          alt="${sessionScope.user.myGoshuinBook.goshuinBookDesign.name}"
					          class="book-cover-img" />

					        <c:forEach var="att" items="${sessionScope.user.myGoshuinBook.attachedStickerList}">
					          <img
					            src="${sessionScope.basePath}/sticker/${att.goshuinBookSticker.imagePath}"
					            alt="${att.goshuinBookSticker.name}"
					            class="book-sticker"
					            style="left:${att.xPos}%; top:${att.yPos}%;"
					          />
					         </c:forEach>
				</div>
            </div>
            <p class="note">※ 御朱印帳一覧から登録できます。</p>
        </div>
          <!-- 🧧 My御朱印帳表示設定 -->
          <div class="form-group toggle-area">
            <label for="showGoshuin">My御朱印帳の表示</label>
            <label class="switch">
              <input type="checkbox" name="isMyGoshuinBookPublic" value="true" id="showGoshuin"
	               <c:if test="${sessionScope.user.isMyGoshuinBookPublic()}">
	               checked
	               </c:if>
	               >
              <span class="slider"></span>
            </label>
          </div>

          <div class="btn-area">
            <a href="Profile.action" class="cancel-btn">キャンセル</a>
            <button type="submit" class="save-btn">保存</button>
          </div>
        </form>

	</c:param>
</c:import>