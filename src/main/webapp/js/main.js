const menuToggle = document.querySelector('.menu-toggle');
const mainNav = document.querySelector('.nav');

if (menuToggle && mainNav) {
  menuToggle.addEventListener('click', () => {
    const isOpen = mainNav.classList.toggle('open');
    menuToggle.setAttribute('aria-expanded', String(isOpen));
  });

  mainNav.querySelectorAll('a').forEach((link) => {
    link.addEventListener('click', () => {
      mainNav.classList.remove('open');
      menuToggle.setAttribute('aria-expanded', 'false');
    });
  });
}

const carousel = document.querySelector('[data-carousel]');
const slides = document.querySelectorAll('.slide');
const dots = document.querySelectorAll('[data-slide]');
const title = document.getElementById('slideTitle');
const subtitle = document.getElementById('slideSubtitle');

// Thời gian chuyển ảnh: 3000 = 3 giây. Có thể đổi thành 2000, 4000, 5000...
const imageDuration = 3000;
let currentIndex = 0;
let timer = null;

function pauseAllVideos() {
  document.querySelectorAll('.slide video').forEach((video) => {
    video.pause();
    video.currentTime = 0;
  });
}

function setActiveDot(index) {
  dots.forEach((dot, dotIndex) => {
    dot.classList.toggle('active', dotIndex === index);
  });
}

function updateCaption(slide) {
  if (title) title.textContent = slide.dataset.title || 'Celine Closet';
  if (subtitle) subtitle.textContent = slide.dataset.subtitle || '';
}

function showSlide(index) {
  if (!slides.length) return;

  clearTimeout(timer);
  pauseAllVideos();

  currentIndex = (index + slides.length) % slides.length;

  slides.forEach((slide, slideIndex) => {
    slide.classList.toggle('active', slideIndex === currentIndex);
  });

  const activeSlide = slides[currentIndex];
  updateCaption(activeSlide);
  setActiveDot(currentIndex);

  if (activeSlide.dataset.kind === 'video') {
    const video = activeSlide.querySelector('video');
    if (!video) return;

    video.onended = () => showSlide(0);
    const playPromise = video.play();

    // Nếu trình duyệt chặn autoplay video, tự quay về ảnh đầu sau 6 giây.
    if (playPromise && typeof playPromise.catch === 'function') {
      playPromise.catch(() => {
        timer = setTimeout(() => showSlide(0), 6000);
      });
    }
  } else {
    timer = setTimeout(() => showSlide(currentIndex + 1), imageDuration);
  }
}

dots.forEach((dot) => {
  dot.addEventListener('click', () => showSlide(Number(dot.dataset.slide)));
});

if (carousel) {
  showSlide(0);
}
