// フッターナビのタブ切り替え
const navButtons = document.querySelectorAll(".nav-btn");
navButtons.forEach(btn => {
  btn.addEventListener("click", () => {
    navButtons.forEach(b => b.classList.remove("active"));
    btn.classList.add("active");
  });
});

// ハンバーガーメニュー操作
const menuBtn = document.getElementById("menu-btn");
const sideMenu = document.getElementById("side-menu");
const overlay = document.getElementById("overlay");

function openMenu() {
  sideMenu.classList.add("active");
  overlay.classList.add("active");
}
function closeMenu() {
  sideMenu.classList.remove("active");
  overlay.classList.remove("active");
}

menuBtn.addEventListener("click", () => {
  sideMenu.classList.toggle("active");
  overlay.classList.toggle("active");
});
overlay.addEventListener("click", closeMenu);

// スワイプ操作
document.addEventListener("DOMContentLoaded", () => {
  const track = document.querySelector(".gallery-track");
  if (!track) return;

  let startX = 0;
  let currentIndex = 0;
  const total = track.children.length;

  function moveTo(index) {
    track.style.transform = `translateX(-${index * 100}%)`;
  }

  // スマホスワイプ
  track.addEventListener("touchstart", (e) => (startX = e.touches[0].clientX));
  track.addEventListener("touchend", (e) => {
    const diff = startX - e.changedTouches[0].clientX;
    handleSwipe(diff);
  });

  // PCドラッグ
  let dragging = false;
  track.addEventListener("mousedown", (e) => {
    dragging = true;
    startX = e.clientX;
  });
  track.addEventListener("mouseup", (e) => {
    if (!dragging) return;
    dragging = false;
    handleSwipe(startX - e.clientX);
  });
  track.addEventListener("mouseleave", () => (dragging = false));

  function handleSwipe(diff) {
    if (diff > 50 && currentIndex < total - 1) currentIndex++;
    else if (diff < -50 && currentIndex > 0) currentIndex--;
    moveTo(currentIndex);
  }
});

document.addEventListener("DOMContentLoaded", () => {
  const stickerLayer = document.getElementById("sticker-layer");
  const stickerOptions = document.querySelectorAll(".sticker-option");

  stickerOptions.forEach(option => {
    option.addEventListener("click", () => {
      const newSticker = document.createElement("img");
      newSticker.src = option.src;
      newSticker.classList.add("sticker");
      newSticker.style.left = "40%";
      newSticker.style.top = "40%";
      stickerLayer.appendChild(newSticker);

      makeDraggable(newSticker);
    });
  });

  function makeDraggable(el) {
    let offsetX = 0, offsetY = 0, startX = 0, startY = 0;

    const startDrag = (e) => {
      e.preventDefault();
      const event = e.touches ? e.touches[0] : e;
      startX = event.clientX;
      startY = event.clientY;
      offsetX = el.offsetLeft;
      offsetY = el.offsetTop;
      document.addEventListener("mousemove", onDrag);
      document.addEventListener("touchmove", onDrag, { passive: false });
      document.addEventListener("mouseup", endDrag);
      document.addEventListener("touchend", endDrag);
    };

    const onDrag = (e) => {
      const event = e.touches ? e.touches[0] : e;
      const dx = event.clientX - startX;
      const dy = event.clientY - startY;
      el.style.left = offsetX + dx + "px";
      el.style.top = offsetY + dy + "px";
    };

    const endDrag = () => {
      document.removeEventListener("mousemove", onDrag);
      document.removeEventListener("touchmove", onDrag);
      document.removeEventListener("mouseup", endDrag);
      document.removeEventListener("touchend", endDrag);
    };

    el.addEventListener("mousedown", startDrag);
    el.addEventListener("touchstart", startDrag);
  }
});

// ⭐ お気に入りボタン
document.addEventListener("DOMContentLoaded", () => {
  const favBtn = document.getElementById("fav-btn");
  if (favBtn) {
    favBtn.addEventListener("click", () => {
      favBtn.classList.toggle("active");
    });
  }

   // ❤️ いいねボタン
  const likeBtns = document.querySelectorAll(".like-btn");
  const message = document.getElementById("like-message");

  likeBtns.forEach(btn => {
    btn.addEventListener("click", () => {
      const countSpan = btn.querySelector(".like-count");
      let count = parseInt(countSpan.textContent);

      if (btn.classList.contains("liked")) {
        // いいね取り消し
        btn.classList.remove("liked");
        count--;
      } else {
        // いいね追加
        btn.classList.add("liked");
        count++;

        if (message) {
          message.classList.add("show");
          setTimeout(() => message.classList.remove("show"), 1500);
        }
      }

      countSpan.textContent = count;
    });
  });
});

/// 🖼️ プロフィール画像プレビュー
document.getElementById("profileImage").addEventListener("change", function (e) {
  const file = e.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = function (e) {
      document.getElementById("preview").src = e.target.result;
    };
    reader.readAsDataURL(file);
  }
});

// 💾 保存ボタン
document.getElementById("profileForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const username = document.getElementById("username").value;
  const showGoshuin = document.getElementById("showGoshuin").checked;

  // LocalStorageに保存（デモ用）
  localStorage.setItem("username", username);
  localStorage.setItem("showGoshuin", showGoshuin);

  alert("プロフィールを保存しました！");
  window.location.href = "profile.html";
});

// 🔁 ページ読み込み時に設定を復元
window.addEventListener("load", () => {
  const savedUsername = localStorage.getItem("username");
  const savedShow = localStorage.getItem("showGoshuin") === "true";

  if (savedUsername) document.getElementById("username").value = savedUsername;
  document.getElementById("showGoshuin").checked = savedShow;
});
document.querySelectorAll(".register-my-btn").forEach(btn => {
  btn.addEventListener("click", () => {
    const imgPath = btn.dataset.img;

    // LocalStorageに登録
    localStorage.setItem("myGoshuinImage", imgPath);

    alert("My御朱印帳として登録しました！");
  });
});

window.addEventListener("load", () => {
  const savedImg = localStorage.getItem("myGoshuinImage");
  const imgElement = document.getElementById("myGoshuinPreview");
  if (savedImg) imgElement.src = savedImg;
});

// ❌ 解除ボタン
document.getElementById("clearMyGoshuin").addEventListener("click", () => {
  localStorage.removeItem("myGoshuinImage");
  document.getElementById("myGoshuinPreview").src = "images/blank.jpg";
  alert("My御朱印帳を解除しました。");
});

document.addEventListener("DOMContentLoaded", () => {
  const buttons = document.querySelectorAll(".add-btn");

  buttons.forEach(btn => {
    const goshuin = {
      id: btn.dataset.id,
      name: btn.dataset.name,
      img: btn.dataset.img,
      url: btn.dataset.url,
    };

    // すでに登録されているかチェック
    const myList = JSON.parse(localStorage.getItem("myGoshuin") || "[]");
    if (myList.some(item => item.id === goshuin.id)) {
      btn.textContent = "✔ 登録済み";
      btn.classList.add("added");
    }

    // クリックで登録
    btn.addEventListener("click", () => {
      let list = JSON.parse(localStorage.getItem("myGoshuin") || "[]");

      if (list.some(item => item.id === goshuin.id)) {
        alert("すでにMy御朱印帳に登録されています。");
        return;
      }

      list.push(goshuin);
      localStorage.setItem("myGoshuin", JSON.stringify(list));

      btn.textContent = "✔ 登録済み";
      btn.classList.add("added");
      alert(`${goshuin.name} をMy御朱印帳に登録しました！`);
    });
  });
});

document.addEventListener("DOMContentLoaded", () => {
  const buttons = document.querySelectorAll(".use-btn");

  buttons.forEach(btn => {
    const couponId = btn.dataset.id;
    const isUsed = localStorage.getItem(`couponUsed_${couponId}`) === "true";

    if (isUsed) {
      btn.textContent = "使用済み";
      btn.classList.add("used");
      btn.disabled = true;
    }

    btn.addEventListener("click", () => {
      if (confirm("この商品券を使用しますか？")) {
        localStorage.setItem(`couponUsed_${couponId}`, "true");
        btn.textContent = "使用済み";
        btn.classList.add("used");
        btn.disabled = true;
      }
    });
  });
});

document.addEventListener("DOMContentLoaded", () => {
  const buttons = document.querySelectorAll(".use-btn");

  buttons.forEach(btn => {
    btn.addEventListener("click", () => {
      const url = btn.dataset.url;

      if (url) {
        // 使用ボタン押下 → その御朱印帳をMy御朱印帳に設定
        const img = btn.closest(".goshuin-item").querySelector("img").src;
        const name = btn.closest(".goshuin-item").querySelector(".goshuin-name").textContent;

        localStorage.setItem("myGoshuinCurrent", JSON.stringify({ name, img, url }));

        alert(`${name} をMy御朱印帳として設定しました。`);
        window.location.href = url;
      }
    });
  });
});
