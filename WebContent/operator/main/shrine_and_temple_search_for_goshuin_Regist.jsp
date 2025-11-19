<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h3>神社仏閣検索</h3>

      <div class="search-box">
        <label for="tag">タグ:</label>
        <select id="tag">
          <option value="">地域</option>
          <option value="神社">神社</option>
          <option value="寺">寺</option>
          <option value="その他">その他</option>
        </select>
        <select id="tag">
          <option value="">ご利益</option>
          <option value="神社">神社</option>
          <option value="寺">寺</option>
          <option value="その他">その他</option>
        </select>

        <label for="name">名称:</label>
        <input type="text" id="name" placeholder="神社・寺名を入力">

        <button>検索</button>
      </div>

      <div id="result">

      </div>
	</c:param>
</c:import>