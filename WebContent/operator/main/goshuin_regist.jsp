<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h3>デジタル御朱印登録</h3>

      <form class="temple-form">
        <label for="name">販売期間:</label>
        <input type="text" id="name" placeholder="例：無期限" required>


        <label for="image">画像アップロード:</label>
        <input type="file" id="image" accept="image/*">

        <button type="submit" class="register-btn">登録</button>
      </form>
	</c:param>
</c:import>