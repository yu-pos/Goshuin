<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
      <form action="ShrineAndTempleSearchExecuteForUpdateInfo.action" method="get" class="search-box">

	      <c:forEach var="tagType" items="${tagTypeMap}">

				<label>${tagType.value}:</label>

			    <select name="tag">
			        <option value="">------</option>

			        <c:forEach var="tag" items="${tagsByType[tagType.key]}">
			            <option value="${tag.id}"
			            <c:if test="${tag.isSelected()}">
			            	selected
			            </c:if>
			            >${tag.name}</option>
			        </c:forEach>

			    </select>
	       	</c:forEach>

	        <label for="name">名称:</label>
	        <input type="text" value="${searchStr}" name="name" id="name" placeholder="神社・寺名を入力">

	        <input type="submit" value="検索">
      </form>

      <div id="result">
       <c:if test="${empty results}">
        <p>検索条件に一致する神社・寺院は見つかりませんでした。</p>
    </c:if>


      	<c:forEach var="result" items="${results}">
	        <div class="result-item">
	          <div class="info">
	            <p><strong>名称:</strong> ${result.name}</p>
	            <c:forEach var="tagType" items="${tagTypeMap}">
	                <c:if test="${result.tagsByType[tagType.key] != null}">
	                    <p><strong>${tagType.value}:</strong>
	                        <c:forEach var="tag" items="${result.tagsByType[tagType.key]}" varStatus="status">
	                            ${tag.name}<c:if test="${!status.last}">・</c:if>
	                        </c:forEach>
	                    </p>
	                </c:if>
            	</c:forEach>
	          </div>
			  <form action="ShrineAndTempleUpdate.action" method="GET">
	          	<input type="hidden" name="shrineAndTempleId" value="${result.id}">
	          	<input type="submit" value="変更" class="register-btn">
	          </form>
	        </div>
        </c:forEach>
        <!-- 結果が多い場合は自動でスクロール -->
      </div>
	</c:param>
</c:import>