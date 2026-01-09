<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h1>口コミを投稿する</h1>

          <form action="ReviewPostExecute.action"  method="POST" id="review-form" enctype="multipart/form-data">
            <!-- ユーザー名（自動入力） -->
            <div class="form-group">
              <label for="username">ユーザー名</label>
              <input type="text" id="username" name="username" value="${sessionScope.user.userName }" readonly>
            </div>

            <!-- 口コミ内容 -->
            <div class="form-group">
              <label for="text">口コミ内容</label>
              <textarea id="text" name="text" rows="4" placeholder="例：雰囲気がとても良かったです。" required></textarea>
            </div>
			<input type="hidden"name="shrineAndTempleId" id="shrineAndTempleId" value="${shrineAndTempleId}">


            <!-- 画像アップロード -->
            <div class="form-group">
              <label for="photo">画像を添付（任意）</label>
              <input type="file" id="photo" name="photo" accept="image/*">
            </div>



            <!-- 投稿ボタン -->
            <div class="form-buttons">
            <a href="ShrineAndTempleInfo.action?id=${shrineAndTempleId}" class="cancel-btn">戻る</a>


             <input type="submit" value="投稿" class="cancel-btn">

            </div>
          </form>
	</c:param>
</c:import>

