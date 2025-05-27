const plants = Array.from({ length: 15 }, (_, i) => ({
  name: `Растение ${i + 1}`,
  description: "Тут будет какое-то описание этого растения",
  image: "https://lifeglobe.net/x/entry/0/8_5.jpg",
}));

const searchInput = document.getElementById("searchInput");
const resultsContainer = document.getElementById("resultsContainer");
const searchContainer = document.getElementById("searchContainer");

// Рендер карточек растений
function renderPlants(list) {
  if (list.length === 0) {
    resultsContainer.innerHTML = `<p class="text-center text-slate-400 col-span-full mt-10">Ничего не найдено</p>`;
    return;
  }

  resultsContainer.innerHTML = list
    .map(
      ({ name, description, image }) => `
    <div class="plant-card w-full min-w-[300px] cursor-pointer" data-description="${description}">
      <img src="${image}" alt="${name}" />
      <div class="plant-info">
        <h2 class="text-xl font-semibold text-slate-100">${name}</h2>
      </div>
    </div>
  `
    )
    .join("");

  observeCards();
}

// Анимация появления карточек
function observeCards() {
  const cards = document.querySelectorAll(".plant-card");
  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          const index = Array.from(entry.target.parentElement.children).indexOf(
            entry.target
          );
          entry.target.style.animationDelay = `${index * 50}ms`;
          entry.target.classList.add("visible");
          observer.unobserve(entry.target);
        }
      });
    },
    { threshold: 0.1 }
  );

  cards.forEach((card) => observer.observe(card));
}

// Фильтрация по поиску
function filterPlants() {
  const q = searchInput.value.trim().toLowerCase();

  if (q.length > 0) {
    searchContainer.classList.add("header-state");
    resultsContainer.classList.add("visible");

    const filtered = plants.filter((p) => p.name.toLowerCase().includes(q));
    renderPlants(filtered);
  } else {
    searchContainer.classList.remove("header-state");
    resultsContainer.classList.remove("visible");
    resultsContainer.innerHTML = "";
  }
}

// Плавный переход при клике на кнопку входа
document.addEventListener("DOMContentLoaded", () => {
  const authLink = document.querySelector("#authButtons a.auth-btn");
  authLink.addEventListener("click", (e) => {
    e.preventDefault();
    const href = authLink.getAttribute("href");
    document.body.classList.add("fade-out");
    setTimeout(() => {
      window.location.href = href;
    }, 500);
  });
});

// Реализация модального окна с блокировкой прокрутки
document.addEventListener("click", (e) => {
  const card = e.target.closest(".plant-card");
  const modal = document.getElementById("plantModal");
  const modalImg = document.getElementById("modalImage");
  const modalTitle = document.getElementById("modalTitle");
  const modalDesc = document.getElementById("modalDescription");

  if (card) {
    const img = card.querySelector("img").src;
    const title = card.querySelector("h2").innerText;
    const desc = card.dataset.description;

    modalImg.src = img;
    modalTitle.textContent = title;
    modalDesc.textContent = desc;

    modal.classList.remove("hidden");
    document.body.classList.add("overflow-hidden");
    setTimeout(() => modal.classList.add("show"), 10);
  }

  if (
    e.target.id === "modalClose" ||
    (e.target.id === "plantModal" && !e.target.closest("#modalContent"))
  ) {
    modal.classList.remove("show");
    document.body.classList.remove("overflow-hidden");
    setTimeout(() => modal.classList.add("hidden"), 300);
  }
});

searchInput.addEventListener("input", filterPlants);
