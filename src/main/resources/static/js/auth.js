const container = document.querySelector(".container");
const registerBtn = document.querySelector(".register-btn");
const loginBtn = document.querySelector(".login-btn");

registerBtn.addEventListener("click", () => {
  container.classList.add("active");
});

loginBtn.addEventListener("click", () => {
  container.classList.remove("active");
});

document.addEventListener("DOMContentLoaded", () => {
  if (!document.body.classList.contains("fadeIn")) {
    document.body.classList.add("fadeIn");
  }
});

window.addEventListener("load", () => {
  document.body.style.opacity = "1";
});
