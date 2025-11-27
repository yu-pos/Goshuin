<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="../base.jsp">
  <c:param name="content">

    <h1>メイン画面</h1>

    <!-- ログインポイントメッセージ -->
    <c:if test="${not empty loginPointMessage}">
      <div class="info-message">
        ${loginPointMessage}
      </div>
    </c:if>

    <!-- おみくじボタン -->
    <section class="omikuji">
      <a href="omikuji.jsp" class="omikuji-btn left" aria-label="おみくじ">
        <img src="${pageContext.request.contextPath}/user/images/omikuji.png" alt="おみくじ" class="omikuji-icon">
      </a>
    </section>

    <!-- ランク情報 -->
    <section class="card rank-section">
      <h2>
        ランク
        <img src="${rankImagePath}" alt="ランクアイコン" class="rank-icon">
      </h2>

      <c:choose>
        <c:when test="${currentRank > 0}">
          <form action="Rank.action" method="get">
            <button class="btn" type="submit">
              ランクアップまであと ${remainingStamp} 個
            </button>
          </form>
        </c:when>
        <c:otherwise>
		  <p>最高ランク(零)です！</p>
		  <p>御朱印 ${goshuinCount} 個 → 商品券 ${couponCount} 枚獲得済み</p>
		  <c:choose>
		    <c:when test="${nextCouponRemaining > 0}">
		      <p>次の商品券まであと ${nextCouponRemaining} 個の御朱印が必要です。</p>
		    </c:when>
		    <c:otherwise>
		      <!-- ここだけ Rank.action に飛ぶ -->
		      <form action="Rank.action" method="get">
		        <button class="btn" type="submit">
		          ちょうど区切りです。次は30個で商品券
		        </button>
		      </form>
		    </c:otherwise>
		  </c:choose>
		</c:otherwise>
      </c:choose>
    </section>

    <!-- イベント情報 -->
    <section class="event-section">
      <h2>⛩️ イベント情報</h2>
      <div class="event-list">
        <c:forEach var="event" items="${eventsView}">
          <a href="EventDetail.action?eventId=${event.id}" class="event-card">
            <img src="${event.imagePath}" alt="${event.title}" class="event-img">
            <div class="event-info">
              <h3>${event.title}</h3>
              <p>${event.text}</p>
              <p>掲載日:
                <fmt:formatDate value="${event.createdAtDate}" pattern="yyyy年MM月dd日 HH:mm"/>
              </p>
            </div>
          </a>
        </c:forEach>

        <c:if test="${empty eventsView}">
          <p>現在、表示できるイベントはありません。</p>
        </c:if>
      </div>
    </section>

  </c:param>
</c:import>