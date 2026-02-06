<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h3>デジタル御朱印登録</h3>

		<c:if test="${not empty errors}">
                <div class="error" style="color:red;">
                    <c:forEach var="e" items="${errors}">
                        <p>${e.value}</p>
                    </c:forEach>
                </div>
        </c:if>
      <form action="GoshuinRegistExecute.action" method="POST" enctype="multipart/form-data" class="temple-form">


        <label for="description">説明:</label>
        <input type="text" id="description" name="description" value="${description}" required>

        <label>販売期間(任意):</label>
        販売開始日:<input type="text" id="name" name="saleStartDate" value="${saleStartDate}" maxlength="5" placeholder="「月-日」で入力 例：01-01" >
        販売終了日:<input type="text" id="name" name="saleEndDate" value="${saleEndDate}" maxlength="5" placeholder="「月-日」で入力 例：01-31" >


        <label for="image">画像アップロード:</label>
        <input type="file" id="image" name="image" accept="image/*" required>

		<input type="hidden" name="shrineAndTempleId" value="${shrineAndTempleId}">
        <div class="button">
	         <button type="button" onclick="history.back()" class="button1">戻る</button>
	         <input type="submit" value="登録" class="button2">
         </div>
      </form>
	</c:param>
</c:import>