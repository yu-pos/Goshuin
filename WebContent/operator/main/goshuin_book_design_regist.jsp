<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h3>表紙デザイン新規登録</h3>

		<c:if test="${not empty errors}">
                <div class="error" style="color:red;">
                    <c:forEach var="e" items="${errors}">
                        <p>${e.value}</p>
                    </c:forEach>
                </div>
        </c:if>

		<c:choose>
		<c:when test = "${type == 'design' }">
	       	<!--前のページで御朱印帳デザイン登録が選ばれた場合はこっち。-->
	     	<form action="GoshuinBookDesignRegistDetail.action" method="POST" class="temple-form">

		       	<label for="name">デザイン名：</label>
		       	<input type="text" id="name" name="groupName" placeholder="例：桜と鳥居" value="${groupName}" maxlength="40" required>

				<label for="count">登録するデザイン数：</label>
		       	<input type="number" id="amount" name="amount" min="1" max="10" value="1" value="${amount}" required>

				<input type="hidden" name="type" value="design">
				<div class="button">
	         		<a href="GoshuinBookDesignList.action" class="button1">戻る</a>


	         		<input type="submit" value="詳細登録へ" class="button2">
         		</div>
         		<!--デザイン登録の場合は詳細登録画面に飛ぶ。-->
	     	</form>
	     </c:when>
		 <c:when test="${type == 'sticker'}">
	     	<!--前のページでステッカー登録が選ばれた場合はこっち。-->
	     	<h3>ステッカー新規登録</h3>
	     	<form action="GoshuinBookStickerRegistExecute.action" method="POST" enctype="multipart/form-data" class="temple-form">
		       	<label for="name">ステッカー名：</label>
		       	<input type="text" id="name" name="name" placeholder="例：狐ステッカー" default="${name}" maxlength="40" required>

		       	<label for="image">ステッカー画像：</label>
		       	<input type="file" id="image" name="image" accept="image/*" required>

				<input type="hidden" name="type" value="sticker">
				<div class="button">
	         		<a href="GoshuinBookDesignList.action" class="button1">戻る</a>
	         		<input type="submit" value="登録" class="button2">
         		</div>
         		<!--ステッカー登録の場合登録ボタンを押したら完了画面に飛ぶ。-->

	     	</form>
	    </c:when>
     	</c:choose>
	</c:param>
</c:import>