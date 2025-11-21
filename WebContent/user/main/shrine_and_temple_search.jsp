<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h1 class="page-title">神社仏閣情報検索</h1>

        <!-- 🏷️ タグ選択フォーム -->
        <section class="tag-select-section">
		<h2>タグから探す</h2>

      <form action="ShrineAndTempleSearchExecute.action" method="get" class="tag-search-box">


	      <c:forEach var="tagType" items="${tagTypeMap}">

			    <select name="tag">
			        <option value="">${tagType.value}</option>

			        <c:forEach var="tag" items="${tagsByType[tagType.key]}">
			            <option value="${tag.id}">${tag.name}</option>
			        </c:forEach>

			    </select>
	       	</c:forEach>

	        <input type="submit" class="search-btn" value="検索">
      	</form>
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