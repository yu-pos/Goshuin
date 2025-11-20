<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>神社仏閣検索結果</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<h1>神社仏閣検索結果</h1>

<c:choose>
    <c:when test="${not empty shrineAndTempleList}">
        <div class="shrine-list">
            <c:forEach var="shrine" items="${shrineAndTempleList}">
                <div class="shrine-card">
                    <img src="${shrine.imagePath}" alt="${shrine.name}" class="shrine-image">
                    <div class="shrine-details">
                        <h2>${shrine.name}</h2>
                        <p><strong>住所:</strong> ${shrine.address}</p>
                        <p><strong>概要:</strong> ${shrine.description}</p>
                        <p><strong>タグ:</strong>
                            <c:forEach var="tag" items="${shrine.tagList}">
                                <span class="tag">${tag.name}</span>
                            </c:forEach>
                        </p>
                        <a href="shrine-detail?id=${shrine.id}" class="detail-button">詳細を見る</a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise>
        <p>該当する神社仏閣は見つかりませんでした。</p>
    </c:otherwise>
</c:choose>

</body>
</html>

