<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base.jsp">
  <c:param name="content">

    <h1>ランク情報</h1>

    <section class="card rank-section">
      <h2>
        現在のランク：${rankName}
        <img src="${rankImagePath}" alt="ランクアイコン" class="rank-icon">
      </h2>

      <c:choose>
        <c:when test="${currentRank > 0}">
          <p>ランクアップまであと ${remainingStamp} 個の御朱印が必要です。</p>
        </c:when>
        <c:otherwise>
          <p>最高ランク(零)です！</p>
          <p>御朱印 ${goshuinCount} 個 → 商品券 ${couponCount} 枚獲得済み</p>
          <c:choose>
            <c:when test="${nextCouponRemaining > 0}">
              <p>次の商品券まであと ${nextCouponRemaining} 個の御朱印が必要です。</p>
            </c:when>
            <c:otherwise>
              <p>ちょうど区切りです。次は30個で商品券がもらえます。</p>
            </c:otherwise>
          </c:choose>
        </c:otherwise>
      </c:choose>
      <a href="Main.action">メイン画面へ</a>
    </section>

  </c:param>
</c:import>