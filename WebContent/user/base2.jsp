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
        <h1 class="header-title">ポケ御朱帳 in 山形</h1>
      </header>



	  <main class="main-content"> ${param.content} </main>

  </div>


  <script src="/goshuin/user/scripts/script.js"></script>