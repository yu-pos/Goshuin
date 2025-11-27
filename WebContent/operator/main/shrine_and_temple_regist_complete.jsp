<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
      <h3>神社仏閣情報登録完了</h3>

      	<a href="${qrImageUrl}">QRコード画像をダウンロード</a><br>
      	(後から神社仏閣編集画面でもダウンロード可能です)
		


      <a href="ShrineAndTempleRegist.action">神社仏閣情報登録画面へ</a>
	</c:param>
</c:import>