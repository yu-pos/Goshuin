<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
    <c:param name="content">
        <div class="page-header">
            <a href="ShrineAndTempleSearch.action" class="back-btn">←<span>戻る</span></a>
            <h1 class="page-title">検索結果</h1>
        </div>

        <!-- 🔍 検索結果リスト -->
        <section class="result-list">
            <c:forEach var="temple" items="${shrineAndTempleList}">
                <div class="temple-card">
                    <img src="${sessionScope.basePath}/shrine_and_temple/${temple.imagePath}" alt="${temple.name}" class="temple-img">
                    <div class="temple-info">
                        <h3>${temple.name}</h3>
                        <c:forEach var="tagType" items="${tagTypeMap}">
			                <c:if test="${temple.tagsByType[tagType.key] != null}">
			                    <p><strong>${tagType.value}:</strong>
			                        <c:forEach var="tag" items="${temple.tagsByType[tagType.key]}" varStatus="status">
			                            ${tag.name}<c:if test="${!status.last}">・</c:if>
			                        </c:forEach>
			                    </p>
			                </c:if>
            			</c:forEach>
                        <a href="ShrineAndTempleInfo.action?id=${temple.id}" class="detail-btn">詳細を見る</a>
                    </div>
                </div>
            </c:forEach>

            <c:if test="${empty shrineAndTempleList}">
                <p class="no-result">該当する神社仏閣は見つかりませんでした。</p>
            </c:if>
        </section>
    </c:param>
</c:import>

