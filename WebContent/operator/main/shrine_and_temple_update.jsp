<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		 <h3>神社仏閣情報編集</h3>

		<c:if test="${not empty errors}">
                <div class="error" style="color:red;">
                    <c:forEach var="e" items="${errors}">
                        <p>${e.value}</p>
                    </c:forEach>
                </div>
        </c:if>
	    <a href="${qrImageUrl}">QRコード画像をダウンロード</a>

      <form action="ShrineAndTempleUpdateExecute.action" method="POST" enctype="multipart/form-data" class="temple-form">
        <label for="name">名称:</label>
        <input type="text" id="name" name="name" value="${shrineAndTemple.name}" maxlength="50" placeholder="例：熊野大社" required>


		<c:forEach var="tagType" items="${tagTypeMap}">

				<label>タグ（${tagType.value}）:</label>

			    <select name="tag">


			        <c:forEach var="tag" items="${tagsByType[tagType.key]}">
			            <option value="${tag.id}"
			            <c:if test="${tag.isSelected()}">
			            	selected
			            </c:if>
			            >${tag.name}</option>
			        </c:forEach>

			    </select>
       	</c:forEach>

        <label for="address">住所:</label>
        <input type="text" id="address" name="address" value="${shrineAndTemple.address}"   maxlength="100" required>

        <label for="description">説明:</label>
        <textarea id="description" rows="4" name="description" maxlength="2000" required>${shrineAndTemple.description}</textarea>

        <label for="info">周辺情報:</label>
        <textarea id="info" rows="3" name="areaInfo" maxlength="1000" required>${shrineAndTemple.areaInfo}</textarea>

        <label for="map">マップ埋め込みリンク:</label>
        <input type="text" id="map" name="mapLink" value="<c:out value="${shrineAndTemple.mapLink}" />" maxlength="1000" required>

        <label for="image">画像変更:</label>
        <input type="file" id="image" name="image" accept="image/*">

		<input type="hidden" name="shrineAndTempleId" value="${shrineAndTemple.id}">
        <input type="submit" value="変更" class="register-btn">
      </form>
	</c:param>
</c:import>