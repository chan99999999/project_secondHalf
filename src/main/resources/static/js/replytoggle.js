const eventRegister2 = function () {
  const replyToggle = document.querySelector('#replyToggle');
  replyToggle.addEventListener('click', handleReplyToggle);
}

const handleReplyToggle = function () {
  const replyListElement = document.querySelector('.review-list');
  replyListElement.classList.toggle('hidden');
}

function main() {
  eventRegister2();
}

main();