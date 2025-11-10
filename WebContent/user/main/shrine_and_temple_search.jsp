<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h1 class="page-title">神社仏閣情報検索</h1>

        <!-- 🏷️ タグ選択フォーム -->
        <section class="tag-select-section">
		<h2>タグから探す</h2>
          <div class="tag-search-box">
            <select id="region-select">
              <option value="">地域</option>
              <option value="yamagata">山形市</option>
              <option value="shinjo">新庄市</option>
              <option value="sagae">寒河江市</option>
            </select>

            <select id="category-select">
              <option value="">ご利益</option>
              <option value="love">恋愛成就</option>
              <option value="study">学業成就</option>
              <option value="safe">安産祈願</option>
              <option value="health">健康祈願</option>
              <option value="work">仕事運</option>
            </select>

            <a href="tag.html" class="search-btn">検索</a>
          </div>
        </section>

        <!-- 🔍 名称検索フォーム -->
        <section class="search-section">
          <label for="search-name">神社・寺院名で検索</label>
          <div class="search-box">
            <input type="text" id="search-name" placeholder="例：上杉神社">
            <a href="name.html" class="search-btn">検索</a>
          </div>
        </section>
	</c:param>
</c:import>