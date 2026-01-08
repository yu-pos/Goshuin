// ==============================
// パスワード表示／非表示 切り替えJS
// 新規登録・ログイン共通
// ==============================
document.addEventListener("DOMContentLoaded", function () {

  function setupToggle(passwordId, toggleId) {
    const pw = document.getElementById(passwordId);
    const toggle = document.getElementById(toggleId);

    if (!pw || !toggle) return;

    toggle.addEventListener("change", function () {
      pw.type = toggle.checked ? "text" : "password";
    });
  }

  // メインパスワード
  setupToggle("password", "togglePassword");

  // 確認用パスワード（あれば）
  setupToggle("passwordConfirm", "togglePasswordConfirm");

});
