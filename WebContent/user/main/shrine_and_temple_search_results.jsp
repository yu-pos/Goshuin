<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<div class="page-header">
          <a href=" temples.html" class="back-btn">←<span>戻る</span></a>
          <h1 class="page-title">○○での検索結果</h1>
        </div>
        <!-- 🔍 検索結果リスト -->
        <section class="result-list">
          <!-- 1件目 -->
          <div class="temple-card">
            <img src="images/126.jpg" alt="上杉神社" class="temple-img">
            <div class="temple-info">
              <h3>上杉神社</h3>
              <p>所在地：山形県米沢市丸の内1丁目4-13</p>
              <p>ご利益：勝負運・出世・学業成就</p>
              <a href="templesview.html" class="detail-btn">詳細を見る</a>
            </div>
          </div>

          <!-- 2件目 -->
          <div class="temple-card">
            <img src="images/kinbo.jpg" alt="金峯神社" class="temple-img">
            <div class="temple-info">
              <h3>金峯神社</h3>
              <p>所在地：山形県鶴岡市青龍寺字金峯1</p>
              <p>ご利益：健康祈願・厄除け・安産祈願</p>
              <a href="temple_detail.html?id=kinbo" class="detail-btn">詳細を見る</a>
            </div>
          </div>

          <!-- 3件目 -->
          <div class="temple-card">
            <img src="images/sagae.jpg" alt="寒河江八幡宮" class="temple-img">
            <div class="temple-info">
              <h3>寒河江八幡宮</h3>
              <p>所在地：山形県寒河江市八幡町5-70</p>
              <p>ご利益：交通安全・家内安全</p>
              <a href="temple_detail.html?id=sagae" class="detail-btn">詳細を見る</a>
            </div>
          </div>
        </section>
	</c:param>
</c:import>