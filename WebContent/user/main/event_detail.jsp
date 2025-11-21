<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base.jsp">
	<c:param name="content">

		<div class="page-header">
			<a href="Event.action" class="back-btn">← <span>戻る</span></a>
          	<h1>${event.title}</h1>
        </div>

		<img src="${event.imagePath}" alt="イベント画像" class="temple-img-large" />

		<section class="temple-info">
         <p>${event.text}</p>
        </section>
	</c:param>
</c:import>