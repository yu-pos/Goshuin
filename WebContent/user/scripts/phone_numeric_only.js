document.addEventListener("DOMContentLoaded", () => {
  const tel = document.getElementById("tel");
  if (!tel) return;

  tel.addEventListener("input", () => {
    tel.value = tel.value.replace(/[^0-9]/g, "");
  });
});
