<%-- 共通テンプレート --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="/goshuin/operator/css/style.css">
  <title>ポケ御朱帳 in 山形</title>
</head>
<body>
  <div id="wrapper">
    <header class="header">
        <div class="logo">ポケ御朱帳 in 山形</div>
        <div class="header-link"><a href="#">ログアウト</a></div>
    </header>

    <aside id="sidebar">
        <section id="side_banner">
            <nav class="nav_content">
                <ul class="nav_list">
                    <li><h4>システムメニュー</h4></li>
                    <li class="nav_item" style="--delay:0.2s;"><a href="main.html">メインメニュー</a></li>
                    <li class="nav_item" style="--delay:0.3s;"><a href="dezi.html">デジタル御朱印登録</a></li>
                    <li class="nav_item" style="--delay:0.4s;"><a href="tourok.html">神社仏閣情報登録</a></li>
                    <li class="nav_item" style="--delay:0.5s;"><a href="henko.html">神社仏閣情報変更</a></li>
                    <li class="nav_item" style="--delay:0.6s;"><a href="gosyutyo.html">御朱印帳登録一覧</a></li>
                    <li class="nav_item" style="--delay:0.7;"><a href="event.html">イベント情報一覧</a></li>
                    <li class="nav_item" style="--delay:0.8s;"><a href="#">アカウント管理</a></li>
                </ul>
            </nav>
        </section>
    </aside>

    <main id="main">
      ${param.content}
    </main>
  </div>
</body>
</html>
