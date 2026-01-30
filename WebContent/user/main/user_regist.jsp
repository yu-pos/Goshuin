<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base2.jsp">
  <c:param name="content">
    <link rel="stylesheet" href="/goshuin/user/css/password_toggle.css?v=1" />
    <link rel="stylesheet" href="/goshuin/user/css/user_regist.css?v=999">
    <!-- 追加：必須・ヒント用（別CSSでOK） -->
    <link rel="stylesheet" href="/goshuin/user/css/user_regist_help.css?v=1">

    <script src="/goshuin/user/scripts/password_toggle.js?v=1"></script>
    <script src="/goshuin/user/scripts/phone_numeric_only.js"></script>

    <div class="register">
      <h1>新規登録</h1>

      <p class="form-note"><span class="required">※</span> は必須入力です。</p>

      <!-- エラーメッセージ表示 -->
      <c:if test="${not empty errors}">
        <div class="error" style="color:red;">
          <c:forEach var="e" items="${errors.values()}">
            <p>${e}</p>
          </c:forEach>
        </div>
      </c:if>

      <form action="UserRegistExecute.action" method="POST" novalidate>
        <!-- ユーザー名 -->
        <label for="userName">ユーザー名 <span class="required">※</span></label>
        <input type="text" id="userName" name="userName"
               value="${userName}"
               required maxlength="20"
               pattern="^[A-Za-z0-9ぁ-んァ-ヶ一-龥ー_]{1,20}$"
               title="1〜20文字。ひらがな/カタカナ/漢字/英数字/_(アンダースコア)が使用できます。"
               placeholder="例：やまがた大好き">
        <small class="hint">1〜20文字で入力してください。
        ひらがな/カタカナ/漢字/英数字/_(アンダースコア)が使用できます。</small>

        <!-- 氏名 -->
        <label for="realName">氏名 <span class="required">※</span></label>
        <input type="text" id="realName" name="realName"
               value="${realName}"
               required maxlength="30"
               pattern="^[A-Za-zぁ-んァ-ヶ一-龥ー\s]{1,30}$"
               title="1〜30文字。漢字/かな/英字/スペースが使用できます。"
               placeholder="例：やまがた 太郎">
        <small class="hint">1〜30文字で入力してください。
        漢字/かな/英字/スペースが使用できます。</small>

        <!-- 生年月日 -->
        <label for="birthDate">生年月日 <span class="required">※</span></label>
        <input type="date" id="birthDate" name="birthDate"
               value="${birthDate}"
               required>
        <small class="hint">カレンダーから選択してください。</small>

        <!-- 住所 -->
        <label for="address">住所 <span class="required">※</span></label>
        <input type="text" id="address" name="address"
               value="${address}"
               required maxlength="60"
               placeholder="例：山形県山形市〇〇">
        <small class="hint">60文字以内で入力してください。</small>

        <!-- 電話番号 -->
        <label for="tel">電話番号 <span class="required">※</span></label>
        <input type="tel" id="tel" name="tel"
               value="${tel}"
               inputmode="numeric"
               maxlength="11"
               pattern="^0\d{9,10}$"
               title="電話番号は0から始まる10〜11桁の数字で入力してください。"
               placeholder="例：09012345678"
               required>
        <small class="hint">0から始まる10〜11桁で入力してください。
        なお、半角数字のみ使用できます。</small>

        <!-- パスワード -->
        <label for="password">パスワード <span class="required">※</span></label>
        <input type="password" id="password" name="password"
               required minlength="8" maxlength="32"
               pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,32}$"
               title="8〜32文字。英字と数字を両方含めてください（記号なし）。"
               placeholder="8〜32文字、英字と数字を含める">
        <small class="hint">8〜32文字で入力してください。
        英字と数字を両方含め、記号なしで入力してください。</small>

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
	  // リロード時にエラーメッセージを消す
	  window.addEventListener('pageshow', function (e) {
	    // bfcache / reload の両方に対応
	    const errorBox = document.querySelector('.error');
	    if (!errorBox) return;

	    // リロード or 戻る時は消す
	    if (e.persisted || performance.getEntriesByType("navigation")[0].type === "reload") {
	      errorBox.remove();
	    }
	  });
	</script>

  </c:param>
</c:import>
