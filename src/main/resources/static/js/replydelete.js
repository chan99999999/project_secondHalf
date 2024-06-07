// 이벤트 위임 함수
const eventRegister4 = function () {
  const reviewListElement = document.querySelector('.review-list');
  reviewListElement.addEventListener('click', async function (event) {
    if (event.target && event.target.id === 'reply-delete-btn') {
      event.stopPropagation(); // 이벤트 전파 중지
      await handleReplyDelete(event);
    }
  });
}

// api를 통해서 댓글 개수 얻어오기
const getReplyCount = async function () {
  const dailyArticleId = getDailyArticleIdFromURL();
  const url = `/daily/reply-count/${dailyArticleId}`;
  const replyCount = await httpRequest(url);
  return replyCount;
}

// 댓글 삭제 이벤트 처리 함수
const handleReplyDelete = async function (event) {

  if (!confirm('정말로 이 댓글을 삭제하시겠습니까?')) {
    return; // 사용자가 취소를 누르면 삭제를 진행하지 않음
  }

  const dailyArticleId = getDailyArticleIdFromURL();
  const url = `/daily/delete-reply`;
  const reviewElement = event.target.closest('.review');
  const replyId = reviewElement.dataset.replyId;

  const deleteReplyData = {
    dailyArticleId: dailyArticleId,
    replyId: replyId
  };

  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(deleteReplyData)
  }

  try {
    const response = await fetch(url, options);

    if (response.ok) {
      // 댓글 목록 업데이트 
      const replyList = await getReplyList();
      const reviewListElement = document.querySelector('.review-list');
      let reviewHTML = '';

      replyList.forEach((reply) => {
        if (reply) {
          const writerHTML = `<li class="review-writer">${reply.writer}</li>`;
          const contentHTML = `<li class="review-content">${reply.content}</li>`;

          const reviewItemHTML = `
      <div class="review" data-reply-id="${reply.replyId}">
        <div>
          <img src="/img/profile.png">
        </div>
        <div>
          <ul>
            ${writerHTML}
            ${contentHTML}
          </ul>
          <button id="reply-delete-btn" type="button" class="btn btn-dark">삭제</button>
        </div>
      </div>
    `;

          reviewHTML += reviewItemHTML;
        }
      });

      reviewListElement.innerHTML = reviewHTML;

      const replyCountElement = document.querySelector('#replyToggle');
      replyCountElement.textContent = `댓글(${await getReplyCount()})`;

    } else {
      const errorMessage = await response.text();
      console.error('댓글 삭제 실패:', errorMessage);
      alert('댓글 삭제에 실패했습니다. 이유: ' + errorMessage);
    }
  } catch (error) {
    console.error('댓글 삭제 중 오류 발생:', error);
  }
}

function main() {
  eventRegister4();
}

main();