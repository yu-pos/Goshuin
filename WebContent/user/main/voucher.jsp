<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../base.jsp">
  <c:param name="content">
    <h1>商品券一覧</h1>
    <p class="note">お手持ちの商品券を確認できます。</p>

    <c:if test="${not empty errorMessage}">
      <p class="error">${errorMessage}</p>
    </c:if>

    <section class="coupon-list">
      <c:choose>
        <c:when test="${hasVoucher}">
          <c:forEach var="voucher" items="${vouchers}">
            <form action="VoucherUse.action" method="post">
              <div class="coupon-card ${voucher.usedAt != null ? 'used' : ''}">
                <img src="${sessionScope.basePath}/voucher/${voucher.imagePath}" alt="商品券画像" class="coupon-img" />
                <div class="coupon-info">
                  <h3>${voucher.description}</h3>
                  <p>発行日: ${voucher.createdAt}</p>
                  <c:if test="${voucher.usedAt != null}">
                    <p class="used-label">※使用済み</p>
                  </c:if>
                </div>
              </div>
              <input type="hidden" name="voucherId" value="${voucher.voucherId}" />
              <input type="submit" value="この商品券を使用する" class="nav-btn" />
            </form>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <p>利用可能な商品券はありません。</p>
        </c:otherwise>
      </c:choose>

      <a href="Main.action">メイン画面へ</a>

    </section>
  </c:param>
</c:import>