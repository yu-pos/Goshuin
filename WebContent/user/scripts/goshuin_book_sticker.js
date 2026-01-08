// ==============================
// 御朱印帳 ステッカー貼り画面 JS（完成・安定版）
//  - パレットのステッカーをタップ/クリック → 御朱印帳に追加
//  - ステッカーはドラッグで移動（指が少し動いたらドラッグ扱い）
//  - 動かなければタップ扱い → 選択（赤枠）→ 削除ボタン表示
//  - PC / スマホ実機 / DevToolsデバイスモードでも安定させる
// ==============================
document.addEventListener("DOMContentLoaded", function () {

  const cover = document.getElementById("goshuinBookCover");
  const deleteBtn = document.getElementById("deleteStickerBtn");

  if (!cover) return;

  // cover上のタッチ操作でページスクロールが混ざらないように保険
  // （CSSの touch-action:none と合わせて効く）
  cover.addEventListener("touchmove", (e) => {
    e.preventDefault();
  }, { passive: false });

  // ---- 削除ボタン更新 ----
  function updateDeleteButton() {
    if (!deleteBtn) return;
    const selected = cover.querySelectorAll(".placed-sticker.selected-for-delete");
    deleteBtn.style.display = (selected.length > 0) ? "inline-block" : "none";
  }

  // ---- 選択トグル ----
  function toggleSelect(sticker) {
    sticker.classList.toggle("selected-for-delete");
    updateDeleteButton();
  }

  // ---- ステッカー初期化（ドラッグ＋タップ選択）----
  function initSticker(sticker) {
    // pointerで統一（マウス/タッチ両対応）
    sticker.style.touchAction = "none";

    let dragging = false;
    let pointerDown = false;

    let startX = 0, startY = 0;
    let offsetX = 0, offsetY = 0;

    // 「タップ」と「ドラッグ」の境界（px）
    // デバイスモードやスマホで誤判定が出やすいので 5px 推奨
    const DRAG_THRESHOLD = 5;

    sticker.addEventListener("pointerdown", (e) => {
      pointerDown = true;
      dragging = false;

      startX = e.clientX;
      startY = e.clientY;

      const stickerRect = sticker.getBoundingClientRect();
      offsetX = e.clientX - stickerRect.left;
      offsetY = e.clientY - stickerRect.top;

      try { sticker.setPointerCapture(e.pointerId); } catch (_) {}

      e.preventDefault();
      e.stopPropagation();
    });

    sticker.addEventListener("pointermove", (e) => {
      if (!pointerDown) return;

      const dx = e.clientX - startX;
      const dy = e.clientY - startY;

      // しきい値を超えたらドラッグ開始
      if (!dragging && (Math.abs(dx) > DRAG_THRESHOLD || Math.abs(dy) > DRAG_THRESHOLD)) {
        dragging = true;
      }

      if (!dragging) return;

      const coverRect = cover.getBoundingClientRect();
      const stickerRect = sticker.getBoundingClientRect();

      let leftPx = e.clientX - coverRect.left - offsetX;
      let topPx  = e.clientY - coverRect.top  - offsetY;

      // 枠内に収める
      const maxLeft = coverRect.width  - stickerRect.width;
      const maxTop  = coverRect.height - stickerRect.height;

      if (leftPx < 0) leftPx = 0;
      if (topPx  < 0) topPx  = 0;
      if (leftPx > maxLeft) leftPx = maxLeft;
      if (topPx  > maxTop)  topPx  = maxTop;

      // %で保存（レスポンシブでも位置維持しやすい）
      const leftPercent = (leftPx / coverRect.width) * 100;
      const topPercent  = (topPx  / coverRect.height) * 100;

      sticker.style.left = leftPercent + "%";
      sticker.style.top  = topPercent + "%";

      e.preventDefault();
      e.stopPropagation();
    });

    sticker.addEventListener("pointerup", (e) => {
      if (!pointerDown) return;
      pointerDown = false;

      // 動いていない＝タップ → 選択
      if (!dragging) {
        toggleSelect(sticker);
      }

      e.preventDefault();
      e.stopPropagation();
    });

    sticker.addEventListener("pointercancel", () => {
      pointerDown = false;
      dragging = false;
    });
  }

  // ---- 既存ステッカー初期化 ----
  cover.querySelectorAll(".placed-sticker").forEach(initSticker);

  document.querySelectorAll(".palette-sticker").forEach((pal) => {
	  pal.addEventListener("pointerup", (e) => {
	    e.preventDefault();
	    e.stopPropagation();

	    const newSticker = document.createElement("img");
	    newSticker.src = pal.src;
	    newSticker.alt = pal.alt || "";
	    newSticker.className = "placed-sticker";
	    newSticker.dataset.stickerId = pal.dataset.stickerId || "";
	    newSticker.style.left = "50%";
	    newSticker.style.top  = "50%";

	    cover.appendChild(newSticker);
	    initSticker(newSticker);
	  });

	  // ★ click を無効化（pointerup のあと click が来て2回になるのを防ぐ）
	  pal.addEventListener("click", (e) => {
	    e.preventDefault();
	    e.stopPropagation();
	  });
	});

  // ---- 削除ボタン ----
  if (deleteBtn) {
    deleteBtn.addEventListener("click", (e) => {
      e.preventDefault();
      const selected = cover.querySelectorAll(".placed-sticker.selected-for-delete");
      selected.forEach((st) => st.remove());
      updateDeleteButton();
    });
  }

  updateDeleteButton();
});
