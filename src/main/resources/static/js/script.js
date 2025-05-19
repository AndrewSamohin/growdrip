const input = document.getElementById('search-input');
const searchContainer = document.getElementById('search-container');
const resultsContainer = document.getElementById('results');
const button = document.getElementById('log-btn');
let moved = false;
const suggestionsContainer = document.getElementById('suggestions');
let allResultsCache = [];

document.addEventListener("DOMContentLoaded", () => {
    const button = document.querySelector("#log-btn");

    button.addEventListener("click", async () => {
        // добавляем класс с анимацией
        document.body.classList.add("fadeOut");

        // Ждем
        await new Promise(resolve => setTimeout(resolve, 500));

        // Переходим
        window.location.href = "auth.html";
    });
});

// регаем кнопку
const clearButton = document.getElementById('clear-search');

// чекаем нажатие
clearButton.addEventListener('click', function() {
  input.value = ''; // чистим
  input.focus(); // фокусировка в поле

  // Проверка если строка уже вверху
  if (moved) {
    searchContainer.classList.remove('search-fixed-top'); // анфикс епт
    searchContainer.classList.add('search-centered');     // бэк в середину
    moved = false;                                       // состояние фолзуем
    resultsContainer.innerHTML = '';                     // чистим 
  }
});

input.addEventListener('input', () => {
  clearButton.classList.toggle('invisible', !input.value); 
});

input.addEventListener('keydown', async (e) => {
  if (e.key === 'Enter' && input.value.trim() !== '') {
    if (!moved) {
      searchContainer.classList.remove('search-centered');
      searchContainer.classList.add('search-fixed-top');
      moved = true;
    }

    // Чистилка
    resultsContainer.innerHTML = '';

    // Типо загружается
    const query = input.value.trim().toLowerCase();

    try {
      if (allResultsCache.length === 0) {
        const response = await fetch(`/search?q=${encodeURIComponent(query)}`); // поиск, подруб к бэку (изменить полностью нужно)
        allResultsCache = await response.json();
      }
  
      const allResults = allResultsCache;

      // Фильтр небольшой
      const filtered = allResults.filter(item =>
        item.title.toLowerCase().includes(query) ||
        item.description.toLowerCase().includes(query)
      );

      if (filtered.length === 0) {
        resultsContainer.innerHTML = `<p class="col-span-3 text-gray-500 text-center">Нет результатов для "${query}"</p>`;
        return;
      }

      // Карточки
      filtered.forEach((item, index) => {
        const card = document.createElement('div');
        card.className = 'card-flip card-appear';
        card.style.animationDelay = `${index * 100}ms`;
        
        card.innerHTML = `
        <div class="card-inner">
          <div class="card-front p-6">
            <img src="${item.image}" alt="${item.title}" class="w-full aspect-square object-cover rounded-md mb-4">
            <h3 class="text-xl font-semibold mb-2">${item.title}</h3>
            <p class="text-gray-500">${item.subtitle || 'Описание...'}</p>
          </div>
          <div class="card-back">
            <h4 class="text-lg font-bold mb-1">Подробнее</h4>
            <p>${item.description || 'Здесь может быть больше информации.'}</p>
          </div>
        </div>
        `;

        // По клику — показываем дополнительную инфу
        card.addEventListener('click', () => {
          // Создаем оверлей
          const overlay = document.createElement('div');
          overlay.className = 'modal-overlay';

          // Создаем карточку для модального окна
          const modal = document.createElement('div');
          modal.className = 'modal-card bg-white rounded-lg shadow-lg p-6';

          // Заполняем модальное окно информацией
          modal.innerHTML = `
            <h2 class="text-2xl font-bold mb-4">${item.title}</h2>
            <p class="text-gray-700 mb-4">${item.description}</p>
            <button class="mt-4 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition">Закрыть</button>
          `;

          const closeBtn = modal.querySelector('button');
          closeBtn.addEventListener('click', () => {
            document.body.removeChild(overlay);
          });

          overlay.addEventListener('click', (event) => {
            if (!modal.contains(event.target)) {
              document.body.removeChild(overlay);
            }
          });

          overlay.appendChild(modal);
          document.body.appendChild(overlay);
        });

        resultsContainer.appendChild(card);
      });
    } catch (err) {
      resultsContainer.innerHTML = `<p class="text-red-500">Ошибка загрузки данных</p>`;
    }
  }
});

input.addEventListener('input', () => {
  if (input.value.trim() === '' && moved) {
    searchContainer.classList.remove('search-fixed-top');
    searchContainer.classList.add('search-centered');
    moved = false;

    resultsContainer.innerHTML = '';
  }
});