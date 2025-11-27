<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<div class="page-header">
          <a href="sanpai.html" class="back-btn">← <span>戻る</span></a>
          <h1 class="page-title">宗派選択</h1>
        </div>
        <div class="nara">
            <p>南都六宗</p>
            <a href="sanpai_temple.jsp" class="choose-btn">法相宗</a>
            <a href="sanpai_temple2.jsp" class="choose-btn">華厳宗</a>
            <a href="sanpai_temple3.jsp" class="choose-btn">律宗</a>
        </div>
        <div class="tensin">
            <p>平安二宗</p>
            <a href="sanpai_temple4.jsp" class="choose-btn">天台宗</a>
            <a href="sanpai_temple5.jsp" class="choose-btn">真言宗</a>
        </div>

        <div class="kamakura">
            <p>鎌倉新仏教</p>
            <a href="sanpai_temple6.jsp" class="choose-btn">浄土宗</a>
            <a href="sanpai_temple7.jsp" class="choose-btn">浄土真宗</a>
            <a href="sanpai_temple8.jsp" class="choose-btn">日蓮宗</a>
            <a href="sanpai_temple9.jsp" class="choose-btn">時宗</a>
            <a href="sanpai_temple10.jsp" class="choose-btn">臨済宗</a>
            <a href="sanpai_temple11.jsp" class="choose-btn">曹洞宗</a>
        </div>

        <div class="edo">
            <p>江戸時代</p>
            <a href="sanpai_temple12.jsp" class="choose-btn">黄檗宗</a>
        </div>
	</c:param>
</c:import>