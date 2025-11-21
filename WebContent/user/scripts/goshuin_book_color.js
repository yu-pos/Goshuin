// ==============================
// 御朱印帳 表紙デザイン選択画面 専用 JS
// ==============================
document.addEventListener("DOMContentLoaded", () => {
  // この画面にだけ存在する要素を取得
  const currentBookImg = document.getElementById("currentBookImg");
  const designRadios = document.querySelectorAll(".design-radio");

  // 要素が無ければ何もしない（他画面で誤動作しないように）
  if (!currentBookImg || designRadios.length === 0) {
    return;
  }

  // ラジオボタン変更時に表紙画像を差し替える
  designRadios.forEach((radio) => {
    radio.addEventListener("change", () => {
      if (!radio.checked) return;

      const path = radio.dataset.imagePath;
      const name = radio.dataset.designName;

      if (path) {
        currentBookImg.src = path;
      }
      if (name) {
        currentBookImg.alt = name;
      }
    });
  });
});
