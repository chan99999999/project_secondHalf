// 이벤트 등록 함수
const eventRegister2 = function () {
  const replyToggle = document.querySelector('#replyToggle');
  replyToggle.addEventListener('click', handleReplyToggle);
}

// 댓글 목록 토글 이벤트 처리 함수
const handleReplyToggle = function () {
  const replyListElement = document.querySelector('.review-list');
  replyListElement.classList.toggle('hidden');
}

function main() {
  eventRegister2();
}

main();