// ==============================
// 御朱印帳 ステッカー貼り画面 専用 JS
// ==============================
document.addEventListener("DOMContentLoaded", () => {
  const cover = document.getElementById("goshuinBookCover");
  const palette = document.getElementById("stickerPalette");

  // この画面じゃない場合は何もしない
  if (!cover || !palette) return;

  // ▼ パレットのステッカーをクリックすると、御朱印帳の上に追加
  palette.querySelectorAll(".palette-sticker").forEach((srcImg) => {
    srcImg.draggable = false; // 画像標準ドラッグを抑制

    srcImg.addEventListener("click", () => {
      const stickerId = srcImg.dataset.stickerId || "";

      const newSticker = document.createElement("img");
      newSticker.src = srcImg.src;
      newSticker.alt = srcImg.alt || "";
      newSticker.classList.add("placed-sticker");
      newSticker.dataset.stickerId = stickerId;

      // とりあえず中央に置いておく
      newSticker.style.left = "50%";
      newSticker.style.top = "50%";

      cover.appendChild(newSticker);

      // ドラッグ可能にする
      makeDraggable(newSticker, cover);
    });
  });

  // ▼ 既に貼られているステッカーもドラッグ可能にする
  cover.querySelectorAll(".placed-sticker").forEach((sticker) => {
    makeDraggable(sticker, cover);
  });

  // ==============================
  // 共通：ステッカーを御朱印帳の中だけでドラッグ
  // ==============================
  function makeDraggable(el, container) {
    let startX, startY, originLeft, originTop;

    const onPointerDown = (ev) => {
      ev.preventDefault();
      const e = ev.touches ? ev.touches[0] : ev;
      startX = e.clientX;
      startY = e.clientY;

      const rect = el.getBoundingClientRect();
      const crect = container.getBoundingClientRect();
      originLeft = rect.left - crect.left;
      originTop = rect.top - crect.top;

      document.addEventListener("mousemove", onPointerMove);
      document.addEventListener("touchmove", onPointerMove, { passive: false });
      document.addEventListener("mouseup", onPointerUp);
      document.addEventListener("touchend", onPointerUp);
    };

    const onPointerMove = (ev) => {
      const e = ev.touches ? ev.touches[0] : ev;
      const dx = e.clientX - startX;
      const dy = e.clientY - startY;

      const crect = container.getBoundingClientRect();
      const srect = el.getBoundingClientRect();

      let newLeft = originLeft + dx;
      let newTop = originTop + dy;

      // 御朱印帳の中に収まるように制限
      newLeft = Math.max(0, Math.min(newLeft, crect.width - srect.width));
      newTop = Math.max(0, Math.min(newTop, crect.height - srect.height));

      const leftPercent = (newLeft / crect.width) * 100;
      const topPercent = (newTop / crect.height) * 100;

      el.style.left = leftPercent + "%";
      el.style.top = topPercent + "%";
    };

    const onPointerUp = () => {
      document.removeEventListener("mousemove", onPointerMove);
      document.removeEventListener("touchmove", onPointerMove);
      document.removeEventListener("mouseup", onPointerUp);
      document.removeEventListener("touchend", onPointerUp);
    };

    el.addEventListener("mousedown", onPointerDown);
    el.addEventListener("touchstart", onPointerDown);
  }
});
