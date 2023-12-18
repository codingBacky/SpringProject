var swiper = new Swiper(".swiper-container", {
  slidesPerView: 3,
  slidesPerGroup: 1,
  spaceBetween: 30,
  loop: true,
  loopedSlides: 5,
  centeredSlides: true,
  autoplay: {
    delay: 2000,
    disableOnInteraction: false,
  },
  pagination: {
    el: ".swiper-pagination",
    clickable: true,
  },
});