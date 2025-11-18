<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../base.jsp">
  <c:param name="content">
    <h1>商品券使用完了</h1>

    <p class="complete-message">
      商品券の使用が正常に完了しました。<br>
      ご利用ありがとうございました。
    </p>

    <div class="complete-icon">
      <img src="images/checkmark.png" alt="完了アイコン" class="check-icon" />
    </div>

    <div class="back-btn-area">
      <form action="menu.html" method="get">
        <input type="submit" value="メイン画面へ" class="back-btn" />
      </form>
    </div>
  </c:param>
</c:import>