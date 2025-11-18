<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h1>メイン画面</h1>
		<c:if test="${not empty loginPointMessage}">
		    <div class="info-message">
		        ${loginPointMessage}
		    </div>
		</c:if>

        <section class="omikuji">
            <button class="omikuji-btn left" aria-label="おみくじ">
                <img src="../images/omikuji.png" alt="おみくじ" class="omikuji-icon">
            </button>
        </section>

        <section class="card rank-section">
            <h2>
                ランク
                <img src="../images/123.png" alt="ランクアイコン" class="rank-icon">
            </h2>
            <button class="btn">ランクアップまであと〇個</button>
        </section>

        <section class="event-section">
        <h2>⛩️ イベント情報</h2>

        <div class="event-list">
            <a href="event1.html" class="event-card">
            <img src="../images/124.jpeg" alt="秋の紅葉祭り" class="event-img">
            <div class="event-info">
                <h3>紅葉情報</h3>
                <p>開催日：11月〜</p>
            </div>
            </a>

            <a href="event2.html" class="event-card">
            <img src="../images/125.jpg" alt="初詣限定御朱印" class="event-img">
            <div class="event-info">
                <h3>戸沢神社初詣</h3>
                <p>開催日：1月1日〜1月3日</p>
            </div>
            </a>

            <a href="event2.html" class="event-card">
            <img src="../images/126.jpg" alt="夏の灯籠祭り" class="event-img">
            <div class="event-info">
                <h3>上杉まつり</h3>
                <p>開催日：4月29日〜5月日日</p>
            </div>
            </a>
        </div>
        </section>
	</c:param>
</c:import>