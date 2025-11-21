<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h3>表紙デザイン詳細登録</h3>
      	<!--表紙デザイン新規登録画面で指定した数字分表示する-->
      	<div class="block">

        	<form class="temple-form">

          		<h4>デザイン(1)</h4>
          		<label>画像アップロード：</label>
          		<input type="file" accept="image/*" name="designImg${i}" required>

          		<label>色：</label>
          		<input type="text" name="designColor" value="紫">
        	</form>
        	<form class="temple-form">
          		<h4>デザイン(2)</h4>
          		<label>画像アップロード：</label>
          		<input type="file" accept="image/*" name="designImg${i}" required>

          		<label>色：</label>
          		<input type="text" name="designColor" value="紫">
        	</form>
      	</div>
        <div class="button">
            <button type="button" class="button1">戻る</button>
            <button type="button" class="button2">登録</button>
        </div>
	</c:param>
</c:import>