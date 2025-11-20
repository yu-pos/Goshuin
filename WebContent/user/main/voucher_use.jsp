<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../base.jsp">
  <c:param name="content">
    <h1>商品券の利用方法</h1>
    <p class="coupon-note">
      この画面を店員にお見せください。<br>
      使用後は「使用する」ボタンを押してください。
    </p>

    <c:if test="${not empty voucher}">
      <div class="coupon-detail">
        <img src="${voucher.imagePath}" alt="商品券画像" class="coupon-detail-img" />
        <h2>${voucher.description}</h2>
        <p>発行日：${voucher.createdAt}</p>
        <c:if test="${voucher.usedAt != null}">
          <p class="used-label">※この商品券はすでに使用済みです</p>
        </c:if>
      </div>

      <c:if test="${voucher.usedAt == null}">
        <div class="use-btn-area">
          <form action="VoucherUseExecute.action" method="post">
            <input type="hidden" name="voucherId" value="${voucher.id}" />
            <input type="submit" value="使用する" class="use-btn-large" />
          </form>
        </div>
      </c:if>

      <div class="use-btn-area">
        <form action="Voucher.action" method="get">
          <input type="submit" value="使用をキャンセルして戻る" class="cancel-btn" />
        </form>
      </div>
    </c:if>

    <c:if test="${empty voucher}">
      <p class="error">商品券情報が取得できませんでした。</p>
    </c:if>
  </c:param>
</c:import>