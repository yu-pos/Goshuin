<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<h3>神社仏閣情報登録</h3>

      <form class="temple-form">
        <label for="name">名称:</label>
        <input type="text" id="name" placeholder="例：熊野大社" required>

        <label for="tag">地域タグ:</label>
        <select id="tag">
          <option value="">選択してください</option>
          <option value="神社">神社</option>
          <option value="寺">寺</option>
          <option value="その他">その他</option>
        </select>
        <label for="tag">ご利益タグ:</label>
        <select id="tag">
          <option value="">選択してください</option>
          <option value="神社">神社</option>
          <option value="寺">寺</option>
          <option value="その他">その他</option>
        </select>

        <label for="address">住所:</label>
        <input type="text" id="address" placeholder="例：山形県南陽市宮内3476-1" required>

        <label for="description">説明:</label>
        <textarea id="description" rows="4" placeholder="神社・寺の簡単な説明を入力"></textarea>

        <label for="info">周辺情報:</label>
        <textarea id="info" rows="3" placeholder="近くの観光地、駐車場、最寄り駅など"></textarea>

        <label for="map">マップ埋め込みリンク:</label>
        <input type="url" id="map" placeholder="Googleマップ埋め込みURLを入力">

        <label for="image">画像アップロード:</label>
        <input type="file" id="image" accept="image/*">

        <button type="submit" class="register-btn">登録</button>
	</c:param>
</c:import>