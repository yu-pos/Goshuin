<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h1>口コミを投稿する</h1>

          <form id="review-form">
            <!-- ユーザー名（自動入力） -->
            <div class="form-group">
              <label for="username">ユーザー名</label>
              <input type="text" id="username" name="username" value="山形太郎" readonly>
            </div>

            <!-- 口コミ内容 -->
            <div class="form-group">
              <label for="review">口コミ内容</label>
              <textarea id="review" name="review" rows="4" placeholder="例：雰囲気がとても良かったです。"></textarea>
            </div>



            <!-- 画像アップロード -->
            <div class="form-group">
              <label for="photo">画像を添付（任意）</label>
              <input type="file" id="photo" name="photo" accept="image/*">
            </div>

            <!-- 投稿ボタン -->
            <div class="form-buttons">
              <a href="templesview.html" class="cancel-btn">戻る</a>
              <a href="kutikomi2.html" class="cancel-btn">投稿</a>
            </div>
          </form>
	</c:param>
</c:import>

