// ==============================
// 御朱印帳 ステッカー貼り画面 JS
//  - パレットのステッカーをタップ → 御朱印帳の中央に出す
//  - 御朱印帳上のステッカーはドラッグで動かせる
//  - ステッカーをタップで選択（赤枠）→ 「選択したステッカーを削除」
// ==============================
document.addEventListener("DOMContentLoaded", function () {

  const cover = document.getElementById("goshuinBookCover");
  const deleteBtn = document.getElementById("deleteStickerBtn");

  if (!cover) {
    console.log("[DEBUG] goshuinBookCover が見つかりません");
    return;
  }
  console.log("[DEBUG] goshuinBookCover 初期化");

  // ---- 共通: マウス/タッチの座標を取るヘルパー ----
  function getPoint(ev) {
    return ev.touches ? ev.touches[0] : ev;
  }

  // ---- 削除ボタンの表示/非表示更新 ----
  function updateDeleteButton() {
    if (!deleteBtn) return;
    const selected = cover.querySelectorAll(".placed-sticker.selected-for-delete");
    if (selected.length > 0) {
      deleteBtn.style.display = "inline-block";
    } else {
      deleteBtn.style.display = "none";
    }
  }

  // ---- ステッカーの選択／解除（赤くする） ----
  function toggleSelect(sticker) {
    sticker.classList.toggle("selected-for-delete");
    updateDeleteButton();
  }

  // ---- 共通: ステッカーをドラッグ移動させる処理 ----
  function startDrag(sticker, ev) {
    const pt = getPoint(ev);
    const coverRect = cover.getBoundingClientRect();
    const stickerRect = sticker.getBoundingClientRect();

    let offsetX = pt.clientX - stickerRect.left;
    let offsetY = pt.clientY - stickerRect.top;
    let moved   = false;  // ← ドラッグしたかどうか判定用

    function move(e2) {
      const p2 = getPoint(e2);
      let leftPx = p2.clientX - coverRect.left - offsetX;
      let topPx  = p2.clientY - coverRect.top  - offsetY;

      // 1px 以上動いたらドラッグと判断
      if (Math.abs(leftPx) > 1 || Math.abs(topPx) > 1) {
        moved = true;
      }

      // 枠からはみ出ないように制限
      const maxLeft = coverRect.width  - stickerRect.width;
      const maxTop  = coverRect.height - stickerRect.height;
      if (leftPx < 0) leftPx = 0;
      if (topPx  < 0) topPx  = 0;
      if (leftPx > maxLeft) leftPx = maxLeft;
      if (topPx  > maxTop)  topPx  = maxTop;

      const leftPercent = (leftPx / coverRect.width)  * 100;
      const topPercent  = (topPx  / coverRect.height) * 100;

      sticker.style.left = leftPercent + "%";
      sticker.style.top  = topPercent + "%";

      e2.preventDefault();
    }

    function end() {
      document.removeEventListener("mousemove", move);
      document.removeEventListener("touchmove", move);
      document.removeEventListener("mouseup", end);
      document.removeEventListener("touchend", end);

      // ドラッグしていた場合は、次の click を一度だけ無視する
      if (moved) {
        sticker.dataset.skipClickOnce = "true";
      }
    }

    document.addEventListener("mousemove", move);
    document.addEventListener("touchmove", move, { passive: false });
    document.addEventListener("mouseup", end);
    document.addEventListener("touchend", end);
  }

  // ---- 既存ステッカー / 新規ステッカー共通の初期化 ----
  function initSticker(sticker) {
    // ドラッグ
    sticker.addEventListener("mousedown", (e) => {
      e.preventDefault();
      startDrag(sticker, e);
    });
    sticker.addEventListener("touchstart", (e) => {
      e.preventDefault();
      startDrag(sticker, e);
    });

    // タップで選択／解除
    sticker.addEventListener("click", (e) => {
      // 直前にドラッグしていた場合は、この1回だけ click を無視
      if (sticker.dataset.skipClickOnce === "true") {
        sticker.dataset.skipClickOnce = "false";
        return;
      }
      e.preventDefault();
      e.stopPropagation();
      toggleSelect(sticker);
    });
  }

  // ---- 1. すでに御朱印帳に貼ってあるステッカーを初期化 ----
  cover.querySelectorAll(".placed-sticker").forEach((sticker) => {
    initSticker(sticker);
  });

  // ---- 2. パレットのステッカーをタップ → 御朱印帳に新規ステッカー追加 ----
  document.querySelectorAll(".palette-sticker").forEach((pal) => {

    pal.addEventListener("click", (ev) => {
      ev.preventDefault();
      console.log("[DEBUG] パレットクリック:", pal.dataset.stickerId);

      const newSticker = document.createElement("img");
      newSticker.src = pal.src;
      newSticker.alt = pal.alt || "";
      newSticker.className = "placed-sticker";
      newSticker.dataset.stickerId = pal.dataset.stickerId || "";

      // とりあえず御朱印帳の中央に出す
      newSticker.style.left = "50%";
      newSticker.style.top  = "50%";

      cover.appendChild(newSticker);

      // ドラッグ & タップ選択を有効化
      initSticker(newSticker);
    });

    // タッチ端末用（click 発火させるためのダミー）
    pal.addEventListener("touchstart", (ev) => {
      // ここでは何もしない（ブラウザがあとで click を出す）
    }, { passive: true });
  });

  // ---- 3. 「選択したステッカーを削除」ボタン ----
  if (deleteBtn) {
    deleteBtn.addEventListener("click", () => {
      const selected = cover.querySelectorAll(".placed-sticker.selected-for-delete");
      selected.forEach((st) => st.remove());
      updateDeleteButton(); // 0 になったらボタン非表示
    });
  }

});
