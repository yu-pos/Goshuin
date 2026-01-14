<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:import url="../base.jsp">
	<c:param name="content">
		<h1>イベント情報</h1>


      <section class="event2-section">
      <c:choose>
        <c:when test="${hasEvent}">
          <c:forEach var="event" items="${events}">
            <form action="EventDetail.action" method="post">
              <div class="event-info">
                <img src="${event.imagePath}" alt="イベント画像" class="event-img" />
                <div class="event-info">
                  <h3>${event.title}</h3>
                </div>
              </div>
              <input type="hidden" name="eventId" value="${event.id}" />
              <input type="submit" value="詳細確認" class="nav-btn" />
            </form>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <p>イベント情報はまだありません</p>
        </c:otherwise>
      </c:choose>
      <a href="Main.action">メイン画面へ</a>
    </section>

	</c:param>
</c:import>