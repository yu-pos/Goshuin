<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<div class="page-header">
          <a href="sanpai_choose.jsp" class="back-btn">← <span>戻る</span></a>
          <h1 class="page-title">神社</h1>
        </div>

        <!-- 📸 神社画像 -->
        <img src="images/131.png" alt="上杉神社" class="temple-img-large">

        <!-- 📖 情報 -->
        <section class="temple-info">
          <p><strong>一般的な参拝方法：</strong><br>にれいにはくしゅいちれい</p>
          <p><strong>神社の楽しみ方：</strong><br>自然を感じろ</p>
          <p><strong>特殊な参拝方法：</strong><br>〇〇〇〇〇〇〇〇〇〇〇〇〇〇〇〇〇〇〇</p>
        </section>
	</c:param>
</c:import>