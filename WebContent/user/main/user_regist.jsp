<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base2.jsp">
  <c:param name="content">

    <link rel="stylesheet" href="/goshuin/user/css/password_toggle.css?v=1" />
    <link rel="stylesheet" href="/goshuin/user/css/user_regist.css?v=999">
    <link rel="stylesheet" href="/goshuin/user/css/user_regist_help.css?v=1">

    <script src="/goshuin/user/scripts/password_toggle.js?v=1"></script>
    <script src="/goshuin/user/scripts/phone_numeric_only.js"></script>

    <div class="register">
      <h1>新規登録</h1>

      <p class="form-note"><span class="required">※</span> は必須入力です。</p>

      <!-- Action からのエラー表示（形式・重複など） -->
      <c:if test="${not empty errors}">
        <div class="error" style="color:red;">
          <c:forEach var="e" items="${errors.values()}">
            <p>${e}</p>
          </c:forEach>
        </div>
      </c:if>

      <!-- ★ 判定は Action のみ -->
      <form action="UserRegistExecute.action" method="POST" novalidate>

        <!-- ユーザー名 -->
        <label for="userName">ユーザー名 <span class="required">※</span></label>
        <input type="text" id="userName" name="userName"
               value="${userName}"
               required maxlength="20"
               placeholder="例：やまがた大好き">
        <small class="hint">
          1〜20文字。ひらがな/カタカナ/漢字/英数字/_(アンダースコア)が使用できます。
        </small>

        <!-- 氏名 -->
        <label for="realName">氏名 <span class="required">※</span></label>
        <input type="text" id="realName" name="realName"
               value="${realName}"
               required maxlength="30"
               placeholder="例：やまがた 太郎">
        <small class="hint">
          1〜30文字。漢字/かな/英字/スペースが使用できます。
        </small>

        <!-- 生年月日 -->
        <label for="birthDate">生年月日 <span class="required">※</span></label>
        <input type="date" id="birthDate" name="birthDate"
               value="${birthDate}"
               required>
        <small class="hint">
          カレンダーから選択してください。
        </small>

        <!-- 住所 -->
        <label for="address">住所 <span class="required">※</span></label>
        <input type="text" id="address" name="address"
               value="${address}"
               required maxlength="60"
               placeholder="例：山形県山形市〇〇">
        <small class="hint">
          60文字以内で入力してください。
        </small>

        <!-- 電話番号 -->
        <label for="tel">電話番号 <span class="required">※</span></label>
        <input type="tel" id="tel" name="tel"
               value="${tel}"
               inputmode="numeric"
               maxlength="11"
               placeholder="例：09012345678"
               required>
        <small class="hint">
          0から始まる10〜11桁の半角数字で入力してください。
        </small>

        <!-- パスワード -->
        <label for="password">パスワード <span class="required">※</span></label>
        <input type="password" id="password" name="password"
               required maxlength="32"
               placeholder="8〜32文字、英字と数字を含める">
        <small class="hint">
          8〜32文字。英字と数字を両方含め、記号は使えません。
        </small>

        <div class="password-row">
          <label class="showpass">
            <input type="checkbox" id="togglePassword">
            パスワードを表示
          </label>
        </div>

        <input type="submit" value="登録">
      </form>

      <a href="Login.action">ログインはこちら</a>
    </div>

    <script>
      // リロード時に Action エラーを消す（UX調整）
      window.addEventListener('pageshow', function (e) {
        const errorBox = document.querySelector('.error');
        if (!errorBox) return;

        const nav = performance.getEntriesByType("navigation")[0];
        if (e.persisted || (nav && nav.type === "reload")) {
          errorBox.remove();
        }
      });
    </script>

  </c:param>
</c:import>
