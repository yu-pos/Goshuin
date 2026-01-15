<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="ja">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>QRコード読み取り</title>
  <link rel="stylesheet" href="/goshuin/user/css/qr.css">
</head>

<body>
  <div class="phone-frame">
    <div class="phone-notch"></div>

    <div class="qr-screen">
      <video id="cameraView" autoplay playsinline muted></video>

      <div class="scan-area">
        <div class="scan-line"></div>
      </div>

      <div class="qr-text">QRコードを枠内に合わせてください</div>
    </div>
  </div>
  <script src="https://cdn.jsdelivr.net/npm/jsqr@1.4.0/dist/jsQR.min.js"></script>
  <script>
  const cameraView = document.getElementById("cameraView");
  const qrText = document.querySelector(".qr-text");
  let scanning = false;

  async function startCamera() {
    try {
      const stream = await navigator.mediaDevices.getUserMedia({
        video: { facingMode: "environment" },
        audio: false
      });

      cameraView.srcObject = stream;

      // 映像がロードされるまで待つ
      cameraView.onloadeddata = () => {
        qrText.textContent = "QRコードを枠内に合わせてください";
        scanning = true;
        requestAnimationFrame(scanLoop);
      };

    } catch (err) {
      console.error("Camera Error:", err);
      qrText.textContent = "カメラの利用を許可してください";
      qrText.style.color = "#ff4444";
    }
  }

  function scanLoop() {
    if (!scanning) return;

    // 映像未ロードなら再試行
    if (cameraView.readyState !== 4) {
      return requestAnimationFrame(scanLoop);
    }

    const canvas = document.createElement("canvas");
    const ctx = canvas.getContext("2d");

    const pattern = new RegExp(
    		  '^https?:\\/\\/' +                                      // http / https
    		  '(' +
    		    'localhost' +                                         // localhost
    		    '|\\d{1,3}(?:\\.\\d{1,3}){3}' +                        // IPv4
    		    '|(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}' +                // domain
    		  ')' +
    		  '(?::\\d+)?' +                                          // port (optional)
    		  '\\/goshuin1?\\/user\\/main\\/GoshuinChoose\\.action' +   // path
    		  '\\?shrineAndTempleId=\\d+' +                            // query
    		  '$'
    		);

    canvas.width = cameraView.videoWidth;
    canvas.height = cameraView.videoHeight;

    ctx.drawImage(cameraView, 0, 0, canvas.width, canvas.height);

    const imageData = ctx.getImageData(0, 0, canvas.width, canvas.height);
    const code = jsQR(imageData.data, imageData.width, imageData.height);

    if (code) {
    	qrText.textContent = "読み取り成功: " + code.data;
        qrText.style.color = "#00ff55";

      if (code.data !== undefined && pattern.test(code.data)) {
          scanning = false;


          // 読み取り後にページ遷移
          setTimeout(() => {
            window.location.href = code.data;
          }, 1000);
      } else {
    	  qrText.textContent = "不正なQRコードです";
          qrText.style.color = "#ff0000";
          requestAnimationFrame(scanLoop);
      }

    } else {
      requestAnimationFrame(scanLoop);
    }
  }

  startCamera();

  </script>
</body>
</html>
