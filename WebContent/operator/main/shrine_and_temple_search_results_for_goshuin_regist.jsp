<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		      <h3>神社仏閣検索</h3>

      <div class="search-box">
        <label for="tag1">タグ:</label>
        <select id="tag1">
          <option value="">地域</option>
          <option value="山形市">山形市</option>
          <option value="鶴岡市">鶴岡市</option>
          <option value="酒田市">酒田市</option>
        </select>

        <select id="tag2">
          <option value="">ご利益</option>
          <option value="交通安全">交通安全</option>
          <option value="学業成就">学業成就</option>
          <option value="商売繁盛">商売繁盛</option>
        </select>

        <label for="name">名称:</label>
        <input type="text" id="name" placeholder="神社・寺名を入力">

        <button>検索</button>
      </div>

      <div id="result">
        <div class="result-item">
          <div class="info">
            <p><strong>名称:</strong> 熊野大社</p>
            <p><strong>ご利益:</strong> 縁結び・家内安全</p>
            <p><strong>地域:</strong> 南陽市</p>
          </div>
          <button class="register-btn">登録</button>
        </div>

        <div class="result-item">
          <div class="info">
            <p><strong>名称:</strong> 立石寺（山寺）</p>
            <p><strong>ご利益:</strong> 学業成就・厄除け</p>
            <p><strong>地域:</strong> 山形市</p>
          </div>
          <button class="register-btn">登録</button>
        </div>

        <!-- 結果が多い場合は自動でスクロール -->
      </div>
	</c:param>
</c:import>