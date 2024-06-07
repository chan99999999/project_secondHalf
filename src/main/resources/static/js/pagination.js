// 이벤트 등록 함수
const eventRegister = function () {
  const paginationLinks = document.querySelectorAll('.pagination a');
  paginationLinks.forEach(link => {
    link.addEventListener('click', handlePageClick);
  });
}

// 페이지 클릭 이벤트 처리 함수
const handlePageClick = function (event) {
  // 페이지 새로 고침 막기
  event.preventDefault();

  const nowonNewsElement = document.querySelector('#nowon-news-section');
  nowonNewsElement.style.display = 'none';

  // 페이지 이동
  window.location.href = this.href;
}

const checkRequestPageParam = function () {
  const urlParams = new URLSearchParams(window.location.search);
  const requestPage = urlParams.get('requestPage');
  const nowonNewsElement = document.querySelector('#nowon-news-section');

  if (nowonNewsElement) {
    if (requestPage && requestPage != '1') {
      nowonNewsElement.style.display = 'none';
    } else {
      nowonNewsElement.style.display = 'block';
    }
  }
}

function main() {
  eventRegister();
  // checkRequestPageParam();
}

main();