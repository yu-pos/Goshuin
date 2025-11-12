<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<div class="point-confirm">
          <h1>ポイント交換確認</h1>
          <p>以下のアイテムを本当に交換しますか？</p>

          <!-- 交換対象アイテム表示 -->
          <div class="confirm-item">
            <img src="images/130.png" alt="御朱印帳表紙" class="confirm-img">
            <h3>カバステッカー</h3>
            <p>必要ポイント：<span class="need-point">2 pt</span></p>
          </div>

          <!-- ボタン配置 -->
          <div class="confirm-buttons">
            <a href="points.html" class="btn back-btn">戻る</a>
            <a href="points3.html" class="btn exchange-btn">交換する</a>
          </div>
        </div>
	</c:param>
</c:import>