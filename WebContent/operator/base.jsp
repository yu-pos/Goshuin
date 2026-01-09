<%-- 共通テンプレート --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${empty sessionScope.operator}">
	<jsp:forward page="/operator/main/Login.action" />
</c:if>

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
        <div class="header-link"><a href="LogoutExecute.action">ログアウト</a></div>
    </header>

    <aside id="sidebar">
        <section id="side_banner">
            <nav class="nav_content">
                <ul class="nav_list">
                    <li><h4>システムメニュー</h4></li>
                    <li class="nav_item" style="--delay:0.2s;"><a href="main.jsp">メインメニュー</a></li>
                    <li class="nav_item" style="--delay:0.3s;"><a href="ShrineAndTempleSearchForRegistGoshuin.action">デジタル御朱印登録</a></li>
                    <li class="nav_item" style="--delay:0.4s;"><a href="ShrineAndTempleRegist.action">神社仏閣情報登録</a></li>
                    <li class="nav_item" style="--delay:0.5s;"><a href="ShrineAndTempleSearchForChangeInfo.action">神社仏閣情報変更</a></li>
                    <li class="nav_item" style="--delay:0.6s;"><a href="GoshuinBookDesignList.action">御朱印帳デザイン管理</a></li>
                    <li class="nav_item" style="--delay:0.7s;"><a href="EventListForOperator.action">イベント情報管理</a></li>
                    <c:if test="${sessionScope.operator.isAdmin()}">
	                    <li class="nav_item" style="--delay:0.8s;"><a href="OperatorRegistConfirm.action">アカウント発行</a></li>
	                    <li class="nav_item" style="--delay:0.9s;"><a href="OperatorAccountList.action">アカウント管理</a></li>
                    </c:if>
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
