<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h3>イベント情報変更</h3>

      <form class="temple-form">
        <label for="name">名称:</label>
        <input type="text" id="name" placeholder="例：秋祭り" required>

        <label for="image">画像アップロード:</label>
        <input type="file" id="image" accept="image/*">

        <label for="description">説明:</label>
        <textarea id="description" rows="4" placeholder="イベント情報を入力"></textarea>

        <button type="submit" class="register-btn">変更</button>
      </form>
	</c:param>
</c:import>