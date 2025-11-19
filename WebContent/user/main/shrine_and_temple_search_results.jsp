<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
    <c:param name="content">
        <div class="page-header">
            <a href="temples.html" class="back-btn">←<span>戻る</span></a>
            <h1 class="page-title">${searchKeyword}での検索結果</h1>
        </div>

        <!-- 🔍 検索結果リスト -->
        <section class="result-list">
            <c:forEach var="temple" items="${templeList}">
                <div class="temple-card">
                    <img src="${temple.imagePath}" alt="${temple.name}" class="temple-img">
                    <div class="temple-info">
                        <h3>${temple.name}</h3>
                        <p>所在地：${temple.address}</p>
                        <p>ご利益：${temple.benefits}</p>
                        <a href="temple_detail.html?id=${temple.id}" class="detail-btn">詳細を見る</a>
                    </div>
                </div>
            </c:forEach>

            <c:if test="${empty templeList}">
                <p class="no-result">該当する神社仏閣は見つかりませんでした。</p>
            </c:if>
        </section>
    </c:param>
</c:import>                   //
