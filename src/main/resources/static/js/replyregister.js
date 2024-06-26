// 이벤트 등록 함수
const eventRegister3 = function () {
  const replyRegisterBtn = document.querySelector('#write-review-btn');
  replyRegisterBtn.addEventListener('click', handleReplyRegister);
}

// 상세보기 페이지 url에서 동적으로 카테고리 번호 얻어오기
const getCategoryIdFromURL = () => {
  const path = window.location.pathname;
  const pathParts = path.split('/');
  const categoryIdStr = pathParts[2]; // URL의 두 번째 부분에서 카테고리 번호 추출
  const categoryId = parseInt(categoryIdStr, 10);
  if (isNaN(categoryId)) {
    throw new Error('Invalid categoryId');
  }
  return categoryId;
};

// api를 통해서 댓글 목록 얻어오기
const getReplyList = async function () {
  const dailyArticleId = getDailyArticleIdFromURL();
  const url = `/daily/getreplylist/${dailyArticleId}`;
  const replyList = await httpRequest(url);
  return replyList;
}

// 댓글 등록 이벤트 처리 함수
const handleReplyRegister = async function (event) {

  const replyContentInput = document.querySelector('textarea[name="content"]');
  const replyContent = replyContentInput.value;

  const categoryId = getCategoryIdFromURL();
  const dailyArticleId = getDailyArticleIdFromURL();
  const memberId = await getLoginMemberId();

  const url = `/daily/${categoryId}/read/${dailyArticleId}`;

  const replyData = {
    writer: memberId,
    content: replyContent
  };

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
      // 댓글 등록 성공 시 입력창 비우기
      replyContentInput.value = '';

      const memberId = await getLoginMemberId();

      // 댓글 목록 DOM 처리
      const replyList = await getReplyList();


      let reviewListElement = document.querySelector('.review-list');

      // reviewListElement가 없을 경우 새로 생성하여 추가
      if (!reviewListElement) {
        reviewListElement = document.createElement('div');
        reviewListElement.className = 'review-list';
        document.body.appendChild(reviewListElement); // 원하는 위치에 추가
      }

      let reviewHTML = '';

      replyList.forEach((reply) => {
        if (reply) {
          const writerHTML = `<li class="review-writer">${reply.writer}</li>`;
          const contentHTML = `<li class="review-content">${reply.content}</li>`;

          // 현재 로그인한 사용자의 댓글일 때만 수정/삭제 버튼을 보이도록 설정
          const isOwnReply = reply.writer === memberId;
          const buttonsHTML = isOwnReply ? `
        <div>
          <button id="reply-update-btn" type="button" class="btn btn-dark">수정</button>
          <button id="reply-delete-btn" type="button" class="btn btn-dark">삭제</button>
        </div>` : '';

          const reviewItemHTML = `
    <div class="review" data-reply-id="${reply.replyId}">
    <div class="review-box">
        <div>
            <img class="commenter" src="/member/image/${reply.picture}">
        </div>
        <div>
            <ul>
            ${writerHTML}
            ${contentHTML}
            </ul>
        </div>
    </div>
    ${buttonsHTML}
</div>
      `;

          reviewHTML += reviewItemHTML;
        }
      });

      reviewListElement.innerHTML = reviewHTML;

      // 댓글 수 DOM 처리
      const replyCountElement = document.querySelector('#replyToggle');
      replyCountElement.textContent = `댓글(${await getReplyCount()})`;

      // 새로 렌더링 댓글 목록에 수정, 삭제 이벤트 등록해주기
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