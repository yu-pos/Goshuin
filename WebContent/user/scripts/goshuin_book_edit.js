//==============================
// 御朱印帳「色選択」画面 専用 JS
//==============================
document.addEventListener("DOMContentLoaded", () => {
  // 現在の御朱印帳画像
  const currentBookImg = document.getElementById("currentBookImg");
  if (!currentBookImg) return; // この画面じゃなければ何もしない

  const designRadios = document.querySelectorAll(".design-radio");

  designRadios.forEach((radio) => {
    radio.addEventListener("change", () => {
      if (!radio.checked) return;

      // JSP 側で data-image-path / data-design-name を持たせておく
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
