// ==============================
// 過去御朱印：横スワイプギャラリー
//  - 1画面に1枚だけ表示
//  - ドラッグ / スワイプで左右に切り替え
// ==============================
document.addEventListener("DOMContentLoaded", function () {
  const gallery = document.querySelector(".goshuin-gallery");
  const track   = document.querySelector(".gallery-track");
  if (!gallery || !track) {
    console.log("[SWIPE] gallery or track not found.");
    return;
  }

  const slides = Array.from(track.querySelectorAll(".goshuin-item"));
  if (slides.length === 0) {
    console.log("[SWIPE] no slides.");
    return;
  }

  let currentIndex   = 0;
  let isDragging     = false;
  let startX         = 0;
  let currentTranslate = 0;
  let prevTranslate    = 0;
  let animationID     = 0;

  const galleryWidth = () => gallery.clientWidth;

  function setSliderPosition() {
    track.style.transform = `translateX(${currentTranslate}px)`;
  }

  function animation() {
    setSliderPosition();
    if (isDragging) {
      requestAnimationFrame(animation);
    }
  }

  function touchStart(index) {
    return function (event) {
      isDragging = true;
      currentIndex = index;

      startX = getPositionX(event);
      prevTranslate = -galleryWidth() * currentIndex;
      currentTranslate = prevTranslate;

      track.style.transition = "none";
      animationID = requestAnimationFrame(animation);
    };
  }

  function touchEnd() {
    if (!isDragging) return;
    isDragging = false;
    cancelAnimationFrame(animationID);

    const movedBy = currentTranslate - prevTranslate;
    const threshold = galleryWidth() * 0.15; // 画面幅の15%以上動いたらページ送り

    if (movedBy < -threshold && currentIndex < slides.length - 1) {
      currentIndex += 1;
    }
    if (movedBy > threshold && currentIndex > 0) {
      currentIndex -= 1;
    }

    currentTranslate = -galleryWidth() * currentIndex;
    prevTranslate    = currentTranslate;

    track.style.transition = "transform 0.3s ease-out";
    setSliderPosition();
  }

  function touchMove(event) {
    if (!isDragging) return;

    const currentPosition = getPositionX(event);
    const diff = currentPosition - startX;
    currentTranslate = prevTranslate + diff;
  }

  function getPositionX(event) {
    return event.type.includes("mouse")
      ? event.pageX
      : event.touches[0].clientX;
  }

  // 各スライドにイベントを付与
  slides.forEach((slide, index) => {
    const start = touchStart(index);

    // マウス
    slide.addEventListener("mousedown", start);
    slide.addEventListener("mousemove", touchMove);
    slide.addEventListener("mouseup", touchEnd);
    slide.addEventListener("mouseleave", touchEnd);

    // タッチ
    slide.addEventListener("touchstart", start, { passive: true });
    slide.addEventListener("touchmove", touchMove, { passive: false });
    slide.addEventListener("touchend", touchEnd);
  });

  // 画像をドラッグしてもブラウザのデフォルトドラッグを抑制
  track.addEventListener("dragstart", (e) => e.preventDefault());

  // 画面サイズ変更時に位置を補正
  window.addEventListener("resize", () => {
    currentTranslate = -galleryWidth() * currentIndex;
    prevTranslate = currentTranslate;
    track.style.transition = "none";
    setSliderPosition();
  });
});
