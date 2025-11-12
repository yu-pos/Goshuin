<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		 <div class="page-header">
          <a href="goshuin.html" class="back-btn">←<span>戻る</span></a>
          <h1 class="page-title">御朱印帳一覧</h1>
        </div>

        <!-- 📚 御朱印帳ギャラリー -->
        <div class="goshuin-list">
          <div class="goshuin-item">
            <a href="goshuin2.html">
              <img src="images/129.png" alt="御朱印帳1">
            </a>
            <button class="add-btn" data-id="goshuin1" data-name="御朱印帳1" data-img="images/129.png" data-url="goshuin2.html">
              ＋ My御朱印帳に登録
            </button>
          </div>

          <div class="goshuin-item">
            <a href="#">
              <img src="images/129.png" alt="御朱印帳2">
            </a>
            <button class="add-btn" data-id="goshuin2" data-name="御朱印帳2" data-img="images/129.png" data-url="#">
              ＋ My御朱印帳に登録
            </button>
          </div>

          <div class="goshuin-item">
            <a href="#">
              <img src="images/129.png" alt="御朱印帳3">
            </a>
            <button class="add-btn" data-id="goshuin3" data-name="御朱印帳3" data-img="images/129.png" data-url="#">
              ＋ My御朱印帳に登録
            </button>
          </div>
        </div>
	</c:param>
</c:import>