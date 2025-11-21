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
    </header>



    <main id="main">
      ${param.content}
    </main>
  </div>
</body>
</html>
