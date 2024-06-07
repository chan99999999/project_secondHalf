// 이벤트 위임 함수
const eventRegister5 = function () {
  const reviewListElement = document.querySelector('.review-list');
  reviewListElement.addEventListener('click', async function (event) {
    if (event.target && event.target.id === 'reply-update-btn') {
      event.stopPropagation(); // 이벤트 전파 중지
      await handleReplyEdit(event);
    }
  });
}

// 댓글 수정 화면 이벤트 처리 함수
const handleReplyEdit = async function (event) {
  
  if (event.target && event.target.id === 'reply-update-btn') {
    const reviewElement = event.target.closest('.review');
    const reviewContentElement = reviewElement.querySelector('.review-content');
    const reviewContent = reviewContentElement.textContent;

    // 수정용 텍스트 영역 생성
    const textarea = document.createElement('textarea');
    textarea.setAttribute('name', 'editedContent');
    textarea.setAttribute('cols', '62');
    textarea.setAttribute('rows', '2');
    textarea.style.fontSize = '24px';
    textarea.textContent = reviewContent;

    // 수정 완료 버튼 생성
    const updateButton = document.createElement('button');
    updateButton.setAttribute('type', 'button');
    updateButton.textContent = '수정 완료';
    updateButton.addEventListener('click', updateReview);

    // 리뷰 내용과 수정 버튼을 수정용 텍스트 영역과 수정 완료 버튼으로 교체
    reviewContentElement.innerHTML = ''; // 내용 초기화
    reviewContentElement.appendChild(textarea);
    event.target.replaceWith(updateButton);
  }
}

// 댓글 수정 완료 이벤트 처리 함수
const updateReview = async function () {
  const replyContentInput = document.querySelector('textarea[name="editedContent"]');
  const replyContent = replyContentInput.value;
  const dailyArticleId = getDailyArticleIdFromURL();
  const url = `/daily/edit-reply`;
  const reviewElement = event.target.closest('.review');
  const replyId = reviewElement.dataset.replyId;

  const updateReplyData = {
    dailyArticleId: dailyArticleId,
    replyId: replyId,
    content: replyContent
  };

  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(updateReplyData)
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
          <button id="reply-update-btn" type="button" class="btn btn-dark">수정</button>
          <button id="reply-delete-btn" type="button" class="btn btn-dark">삭제</button>
        </div>
      </div>
    `;

          reviewHTML += reviewItemHTML;
        }
      });

      reviewListElement.innerHTML = reviewHTML;

    } else {
      const errorMessage = await response.text();
      console.error('댓글 수정 실패:', errorMessage);
      alert('댓글 수정에 실패했습니다. 이유: ' + errorMessage);
    }
  } catch (error) {
    console.error('댓글 수정 중 오류 발생:', error);
  }
}

function main() {
  eventRegister5();
}

main();