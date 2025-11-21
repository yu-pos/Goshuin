<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h3>表紙デザイン新規登録</h3>

       	<!--前のページで御朱印帳デザイン登録が選ばれた場合はこっち。-->
     	<form class="temple-form">

	       	<label for="designName">デザイン名：</label>
	       	<input type="text" id="designName" name="designName" placeholder="例：桜と鳥居">

			<label for="designCount">登録するデザイン数：</label>
	       	<input type="number" id="designCount" name="designCount" min="1" max="10" value="1">

	       	<div class="button">

	         		<button type="button" class="button1">戻る</button>
	         		<button type="button" class="button2">詳細登録へ</button>
	         		<!--デザイン登録の場合は詳細登録画面に飛ぶ。-->
	     	</div>
     	</form>

     	<!--前のページでステッカー登録が選ばれた場合はこっち。-->
     	<h3>ステッカー新規登録</h3>
     	<form class="temple-form">
	       	<label for="designName">デザイン名：</label>
	       	<input type="text" id="designName" name="designName" placeholder="例：桜と鳥居">

	       	<label for="designCount">ステッカーデザイン：</label>
	       	<input type="file" id="designCount" name="designCount" min="1" max="10" value="1">

	       	<div class="button">
	         		<button type="button" class="button1">戻る</button>
	         		<button type="button" class="button2">登録</button>
	         		<!--ステッカー登録の場合登録ボタンを押したら完了画面に飛ぶ。-->
	       	</div>
     	</form>
	</c:param>
</c:import>