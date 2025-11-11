<%-- 共通テンプレート --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="ja">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>ポケ御朱帳 in 山形</title>
  <link rel="stylesheet" href="/goshuin/user/css/style.css">
</head>



<body>
  <div class="phone-frame">
    <div class="phone-notch"></div>

    <div class="phone-screen">
      <header class="app-header">
        <button id="menu-btn" class="header-btn left" aria-label="メニュー">☰</button>
        <h1 class="header-title">ポケ御朱帳 in 山形</h1>
        <button class="header-btn right" aria-label="QRコード">
          <img src="/goshuin/user/images/qr-icon.png" alt="QRコード読み取り" class="qr-icon">
        </button>
      </header>



	  <main class="main-content"> ${param.content} </main>

	  <footer class="footer-nav">
	     	<nav>
	        	<a href="menu.html" class="nav-btn active">🏠<br><span>ホーム</span></a>
	            <a href="goshuin.html" class="nav-btn">📙<br><span>御朱印帳</span></a>
	            <a href="temples.html" class="nav-btn">⛩️<br><span>神社仏閣<br>情報</span></a>
	            <a href="points.html" class="nav-btn">🦃<br><span>ポイント交換</span></a>
	            <a href="profile.html" class="nav-btn">👤<br><span>プロフィール</span></a>
	        </nav>
	   </footer>
       <aside id="side-menu" class="side-menu">
       		<h2>メニュー</h2>
            <ul>
            <li><a href="event.html">📄 イベント一覧</a></li>
            <li><a href="sanpai.html">🙏 参拝方法</a></li>
            <li><a href="ken.html">🎟️ 商品券一覧</a></li>
            <li><a href="logout.html">🚪 ログアウト</a></li>
            </ul>
        </aside>
      <div id="overlay" class="overlay"></div>
    </div>
  </div>


  <script src="/goshuin/user/scripts/script.js"></script>