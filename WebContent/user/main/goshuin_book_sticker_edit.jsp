<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="../base.jsp">
  <c:param name="content">

    <!-- この画面専用 CSS / JS -->
    <link rel="stylesheet" href="/goshuin/user/css/goshuin_book_sticker.css" />
    <script src="/goshuin/user/scripts/goshuin_book_sticker.js"></script>

    <h1 class="page-title">御朱印帳 ステッカー配置</h1>

    <c:if test="${not empty error}">
      <p style="color:red;">${error}</p>
    </c:if>

    <c:if test="${empty goshuinBook}">
      <p>編集できる御朱印帳がありません。</p>
    </c:if>

    <c:if test="${not empty goshuinBook}">
      <!-- 大きめの御朱印帳プレビュー（約2倍イメージ） -->
      <section class="goshuin-book-preview">
        <h2>御朱印帳</h2>
        <div id="goshuinBookCover" class="goshuin-book-cover">
          <img
            src="/goshuin/saved_images/goshuin_book_design/${goshuinBook.goshuinBookDesign.imagePath}"
            alt="${goshuinBook.goshuinBookDesign.name}"
            class="goshuin-book-cover-img" />

          <!-- 既に貼られているステッカー（あれば） -->
          <c:forEach var="att" items="${goshuinBook.attachedStickerList}">
            <img
              src="/goshuin/saved_images/sticker/${att.goshuinBookSticker.imagePath}"
              alt="${att.goshuinBookSticker.name}"
              class="placed-sticker"
              data-sticker-id="${att.goshuinBookSticker.id}"
              style="left:${att.xPos}%; top:${att.yPos}%;" />
          </c:forEach>
        </div>
      </section>

      <!-- ステッカーパレット -->
      <section class="sticker-select">
        <h2>ステッカー</h2>
        <p>下のステッカーをタップして、御朱印帳の上にドラッグで配置できます。</p>

        <div id="stickerPalette" class="sticker-palette">
          <c:forEach var="os" items="${ownedStickerList}">
            <img
              src="/goshuin/saved_images/sticker/${os.goshuinBookSticker.imagePath}"
              alt="${os.goshuinBookSticker.name}"
              class="palette-sticker"
              data-sticker-id="${os.goshuinBookSticker.id}" />
          </c:forEach>
        </div>
      </section>

      <!-- 保存フォーム -->
      <form id="stickerForm" action="GoshuinBookStickerEditExecute.action" method="post">
        <input type="hidden" name="bookId" value="${goshuinBook.id}" />

        <div class="form-buttons">
          <input type="submit" value="ステッカー配置を保存" />
          <a href="GoshuinBookView.action">キャンセル</a>
        </div>
      </form>

      <!-- 送信前にステッカー位置情報を hidden に詰める -->
      <script>
        document.addEventListener("DOMContentLoaded", function () {
          const form = document.getElementById("stickerForm");
          if (!form) return;

          form.addEventListener("submit", function () {
            // 既存 hidden を削除
            const olds = form.querySelectorAll(".sticker-hidden");
            olds.forEach((e) => e.remove());

            const stickers = document.querySelectorAll("#goshuinBookCover .placed-sticker");
            stickers.forEach((st, index) => {
              const id = st.dataset.stickerId || "";
              const left = st.style.left.replace("%", "");
              const top = st.style.top.replace("%", "");

              // stickerId
              const inputId = document.createElement("input");
              inputId.type = "hidden";
              inputId.name = "stickerId";
              inputId.value = id;
              inputId.classList.add("sticker-hidden");
              form.appendChild(inputId);

              // xPos
              const inputX = document.createElement("input");
              inputX.type = "hidden";
              inputX.name = "xPos";
              inputX.value = left;
              inputX.classList.add("sticker-hidden");
              form.appendChild(inputX);

              // yPos
              const inputY = document.createElement("input");
              inputY.type = "hidden";
              inputY.name = "yPos";
              inputY.value = top;
              inputY.classList.add("sticker-hidden");
              form.appendChild(inputY);
            });
          });
        });
      </script>
    </c:if>

  </c:param>
</c:import>
