<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../base.jsp">
	<c:param name="content">
		<div class="omikuji-box">
          <h2 class="omikuji-result">d</h2>
		  <p class="omikuji-message">...</p>
        </div>

        <a href="Main.action" class="close-btn">閉じる</a>
    <script>
    const omikujiData = [
  	  {
  	    result: "✨ 大吉 ✨",
  	    message: `最高の運勢です！新しいことを始めるのに最適な日。
  	笑顔を忘れず進めば、すべてがうまくいくでしょう🌸`
  	  },
  	  {
  	    result: "🌟 中吉 🌟",
  	    message: `良い流れが来ています。努力が実りやすい時期。
  	自信を持って行動しましょう🍀`
  	  },
  	  {
  	    result: "🙂 小吉 🙂",
  	    message: `穏やかに過ごせる一日。焦らずにコツコツ進むと吉。`
  	  },
  	  {
  	    result: "😌 吉 😌",
  	    message: `可もなく不可もなくですが、心穏やかに過ごせば運気UP！`
  	  },
  	  {
  	    result: "⚠️ 末吉 ⚠️",
  	    message: `あと少しでチャンス到来。無理せず備えるのが大切です。`
  	  },
  	  {
  	    result: "💦 凶 💦",
  	    message: `慎重に行動しましょう。落ち着いて判断すれば大丈夫です。`
  	  }
  	];

  	// ランダム選択
  	function showRandomOmikuji() {
  	  const random = Math.floor(Math.random() * omikujiData.length);
  	  const data = omikujiData[random];

  	  // HTML へ反映
  	  document.querySelector(".omikuji-result").textContent = data.result;
  	  document.querySelector(".omikuji-message").innerHTML = data.message.replace(/\n/g, "<br>");
  	}

  	// ページ読み込み時に実行
  	window.addEventListener("DOMContentLoaded", showRandomOmikuji);
  	window.addEventListener("load", () => {
  	  const usernameField = document.getElementById("username");
  	  const showField = document.getElementById("showGoshuin");

  	  const savedUsername = localStorage.getItem("username");
  	  const savedShow = localStorage.getItem("showGoshuin") === "true";

  	  if (usernameField && savedUsername) {
  	    usernameField.value = savedUsername;
  	  }

  	  if (showField) {
  	    showField.checked = savedShow;
  	  }
  	});
	</script>
	</c:param>
</c:import>