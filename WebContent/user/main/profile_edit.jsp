<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h2>プロフィールを編集</h2>

        <!-- 👤 プロフィール画像変更 -->
        <div class="edit-profile-img">
          <img id="preview" src="images/profile-sample.jpg" alt="プロフィール画像" class="profile-img">
          <label for="profileImage" class="change-btn">画像を変更</label>
          <input type="file" id="profileImage" accept="image/*" hidden>
        </div>

        <!-- 🧾 入力フォーム -->
        <form id="profileForm">
          <div class="form-group">
            <label for="username">ユーザー名</label>
            <input type="text" id="username" value="山形太郎">
          </div>


          <div class="form-group">
            <label>My御朱印帳</label>
            <div class="my-goshuin-area">
                <img id="myGoshuinPreview" src="images/blank.jpg" alt="My御朱印帳" class="goshuin-preview">
                <button type="button" id="clearMyGoshuin" class="clear-btn">解除</button>
            </div>
            <p class="note">※ 御朱印帳一覧から登録できます。</p>
        </div>
          <!-- 🧧 My御朱印帳表示設定 -->
          <div class="form-group toggle-area">
            <label for="showGoshuin">My御朱印帳の表示</label>
            <label class="switch">
              <input type="checkbox" id="showGoshuin" checked>
              <span class="slider"></span>
            </label>
          </div>

          <div class="btn-area">
            <a href="profile.html" class="cancel-btn">キャンセル</a>
            <button type="submit" class="save-btn">保存</button>
          </div>
        </form>

	</c:param>
</c:import>