const eventRegister3 = function () {
  const replyRegisterBtn = document.querySelector('#write-review-btn');
  replyRegisterBtn.addEventListener('click', handleReplyRegister);
}

const getCategoryIdFromURL = () => {
  const path = window.location.pathname;
  const pathParts = path.split('/');

  const categoryIdStr = pathParts[2]; // URL의 두 번째 부분에서 카테고리 ID 추출

  const categoryId = parseInt(categoryIdStr, 10);
  if (isNaN(categoryId)) {
    throw new Error('Invalid categoryId');
  }
  return categoryId;
};

// 댓글 목록 가져오기
const getReplyList = async function () {
  const dailyArticleId = getDailyArticleIdFromURL();
  const url = `/daily/getreplylist/${dailyArticleId}`;
  const replyList = await httpRequest(url);
  return replyList;
}


const handleReplyRegister = async function (event) {

  const replyContentInput = document.querySelector('textarea[name="content"]');
  const replyContent = replyContentInput.value;

  const categoryId = getCategoryIdFromURL();
  const dailyArticleId = getDailyArticleIdFromURL();
  const memberId = await getLoginMemberId();

  const replyData = {
    writer: memberId,
    content: replyContent
  };

  const url = `/daily/${categoryId}/read/${dailyArticleId}`;

  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(replyData)
  }

  try {
    const response = await fetch(url, options);

    if (response.ok) {
      // 댓글 등록 성공 후 처리
      replyContentInput.value = ''; // 입력창 비우기
      console.log('댓글이 성공적으로 등록되었습니다.');

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

      const replyCountElement = document.querySelector('#replyToggle');
      replyCountElement.textContent = `댓글(${await getReplyCount()})`;

      const replyUpdateBtns = document.querySelectorAll('#reply-update-btn');
      replyUpdateBtns.forEach(replyUpdateBtn => replyUpdateBtn.addEventListener('click', handleReplyEdit));

      const replyDeleteBtns = document.querySelectorAll('#reply-delete-btn');
      replyDeleteBtns.forEach(replyDeleteBtn => replyDeleteBtn.addEventListener('click', handleReplyDelete));

    } else {
      const errorMessage = await response.text();
      console.error('댓글 등록 실패:', errorMessage);
      alert('댓글 등록에 실패했습니다. 이유: ' + errorMessage);
    }
  } catch (error) {
    console.error('댓글 등록 중 오류 발생:', error);
  }
}


function main() {
  eventRegister3();
}

main();
