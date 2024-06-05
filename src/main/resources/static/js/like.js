const httpRequest = function (url) {
  return fetch(url).then(response => response.json());
}

// 이벤트 등록 함수
const eventRegister = function () {
  const heartImg = document.querySelector('#like');
  heartImg.addEventListener('click', handleHeart);

}

// 상세보기 페이지 url에서 동적으로 일상 게시판 번호 얻어오기
const getDailyArticleIdFromURL = () => {
  const path = window.location.pathname;
  const pathParts = path.split('/');
  const dailyArticleIdStr = pathParts[pathParts.length - 1];
  const dailyArticleId = parseInt(dailyArticleIdStr, 10);
  if (isNaN(dailyArticleId)) {
    throw new Error('Invalid dailyArticleId');
  }
  return dailyArticleId;
};

// api를 통해서 현재 세션에 저장된 회원 아이디 얻어오기
const getLoginMemberId = async function () {
  const loginMember = await httpRequest('/daily/getLoginMember');
  return loginMember.memberId
}

// 좋아요 이벤트 처리 함수
const handleHeart = async function (event) {
  const dailyArticleId = getDailyArticleIdFromURL();
  const memberId = await getLoginMemberId();
  let heartImg = event.target;

  // 현재 상태가 꽉찬 하트이면 다시 체크할 때 false로 변경
  const isCurrentlyLiked = heartImg.getAttribute('src') === '/img/heart.png';
  const newChecked = !isCurrentlyLiked;
  // 서버에 체크값 전달
  const url = `/daily/like?dailyArticleId=${dailyArticleId}&memberId=${memberId}&checked=${newChecked}`;
  
  // 서버에서 전달받은 정보로 DOM 처리
  const object = await httpRequest(url);
  if (object.isUpdated) {
    heartImg.setAttribute('src', newChecked ? '/img/heart.png' : '/img/heart2.png');
    const heartCountArea = document.querySelector('#heartCount');
    heartCountArea.textContent = object.totalHeartCount;
  }
}

function main() {
  eventRegister();
}

main();