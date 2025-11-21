<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base.jsp">
	<c:param name="content">

		<div class="page-header">
          	<form action="Event.action">
				<input type="submit" value="戻る" class="nav-btn" />
			</form>
          	<h1 class="page-title">${event.title}</h1>
        </div>

		<img src="${event.imagePath}"  class="temple-img-large" />

		<section class="temple-info">
         <p>${event.text}</p>
        </section>
	</c:param>
</c:import>