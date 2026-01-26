<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base.jsp">
	<c:param name="content">

		<h3>イベント情報変更</h3>

		<form action="EventUpdateExecute.action" method="post" enctype="multipart/form-data" class="temple-form">

		    <input type="hidden" name="id" value="${event.id}">

		    <label>名称：</label>
		    <input type="text" name="title" value="${event.title}">

		    <label>説明：</label>
		    <textarea name="text">${event.text}</textarea>

		    <label>画像：</label>
		    <input type="file" name="image">

    		<button type="submit"class="register-btn">更新</button>
		</form>



	</c:param>
</c:import>
