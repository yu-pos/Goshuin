<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h3>表紙デザイン詳細登録</h3>
      	<!--表紙デザイン新規登録画面で指定した数字分表示する-->
      	<div class="block">

        	<form action="GoshuinBookDesignRegistExecute.action" method="POST" enctype="multipart/form-data" >
				<c:forEach begin="1" end="${amount}" var="i" >
					<div class="temple-form">
		          		<h4>デザイン(${i})</h4>
		          		<label>画像アップロード：</label>
		          		<input type="file" accept="image/*" name="image${i}" required>

		          		<label>色：</label>
		          		<input type="text" name="color${i}" >
	          		</div>
	          	</c:forEach>
	          	<input type="hidden" name="groupName" value="${groupName}">
	          	<input type="hidden" name="amount" value="${amount}">
	          	<div class="button">
            		<a href="GoshuinBookDesignList.action" type="button" class="button1">戻る</a>
            		<input type="submit" value="登録" class="button2">
        		</div>
        	</form>

      	</div>

	</c:param>
</c:import>