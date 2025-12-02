<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base.jsp">
  <c:param name="content">
    <h3>イベント情報変更</h3>

    <!-- エラーメッセージ表示 -->
    <c:if test="${not empty errorMessage}">
      <p style="color:red;">${errorMessage}</p>
    </c:if>

    <!-- EventRegistExecuteAction を呼ぶフォーム -->
    <form class="temple-form" action="EventRegistExecute.action" method="post" enctype="multipart/form-data">
      <label for="name">名称:</label>
      <input type="text" id="name" name="title" placeholder="例：秋祭り" required>

      <label for="image">画像アップロード:</label>
      <input type="file" id="image" name="image" accept="image/*" required>

      <label for="description">説明:</label>
      <textarea id="description" name="text" rows="4" placeholder="イベント情報を入力" required></textarea>

      <button type="submit" class="register-btn">登録</button>
    </form>
  </c:param>
</c:import>