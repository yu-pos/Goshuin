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
        <a href="QrCodeScan.action" class="header-btn right" aria-label="QRコード">
          <img src="/goshuin/user/images/qr-icon.png" alt="QRコード読み取り" class="qr-icon">
        </a>
      </header>



	  <main class="main-content"> ${param.content} </main>

	  <footer class="footer-nav">
	     	<nav>
	        	<a href="Main.action" class="nav-btn active">🏠<br><span>ホーム</span></a>
	            <a href="GoshuinBookView.action" class="nav-btn">📙<br><span>御朱印帳</span></a>
	            <a href="ShrineAndTempleSearch.action" class="nav-btn">⛩️<br><span>神社仏閣<br>情報</span></a>
	            <a href="PointExchange.action" class="nav-btn">🦃<br><span>ポイント交換</span></a>
	            <a href="profile.html" class="nav-btn">👤<br><span>プロフィール</span></a>
	        </nav>
	   </footer>
       <aside id="side-menu" class="side-menu">
       		<h2>メニュー</h2>
            <ul>
            <li><a href="Event.action">📄 イベント一覧</a></li>
            <li><a href="sanpai_choose.jsp">🙏 参拝方法</a></li>
            <li><a href="Voucher.action">🎟️ 商品券一覧</a></li>
            <li><a href="Logout.action">🚪 ログアウト</a></li>
            </ul>
        </aside>
      <div id="overlay" class="overlay"></div>
    </div>
  </div>


  <script src="/goshuin/user/scripts/script.js"></script>