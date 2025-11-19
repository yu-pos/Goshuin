<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		 <div class="main-header">
        <h3>イベント情報一覧</h3>
        <button class="add-btn">＋ イベント登録</button>
      </div>

      <div id="result" class="scroll-area">
        <!-- イベントの例 -->
        <div class="event-card">
          <img src="img/sample_event.jpg" alt="イベント画像" class="event-img">
          <div class="event-info">
            <h4>山形御朱印フェス 2025</h4>
            <p>山形県内の神社・寺院が参加する御朱印スタンプラリーです。</p>
            <button class="edit-btn">変更</button>
          </div>
        </div>

        <div class="event-card">
          <img src="img/sample_event2.jpg" alt="イベント画像" class="event-img">
          <div class="event-info">
            <h4>秋の祈り祭り</h4>
            <p>紅葉の季節に行われる限定御朱印配布イベント。</p>
            <button class="edit-btn">変更</button>

          </div>
        </div>
        <!-- ここに複数イベントが並ぶ -->
      </div>
	</c:param>
</c:import>