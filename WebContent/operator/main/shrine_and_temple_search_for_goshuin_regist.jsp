<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h3>神社仏閣検索</h3>

      <form action="ShrineAndTempleSearchExecuteForRegistGoshuin.action" method="get" class="search-box">


	      <c:forEach var="tagType" items="${tagTypeMap}">

				<label>${tagType.value}:</label>

			    <select name="tag">
			        <option value="">------</option>

			        <c:forEach var="tag" items="${tagsByType[tagType.key]}">
			            <option value="${tag.id}">${tag.name}</option>
			        </c:forEach>

			    </select>
	       	</c:forEach>

	        <label for="name">名称:</label>
	        <input type="text" id="name" placeholder="神社・寺名を入力">

	        <input type="submit" value="検索">
      </form>

      <div id="result">

      </div>
	</c:param>
</c:import>