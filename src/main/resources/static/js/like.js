const httpRequest = function (url) {
  return fetch(url).then(response => response.json());
}

const eventRegister = function () {
  const heartImg = document.querySelector('#like');
  heartImg.addEventListener('click', handleHeart);

}


// 상세 보기 페이지에서 동적으로 일상 게시판 번호 얻어오기
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

const handleHeart = async function (event) {

  // const dailyArticleId = 30;
  const dailyArticleId = getDailyArticleIdFromURL();
  const memberId = 'sunday';
  let heartImg = event.target;
  // 현재 상태가 꽉찬 하트이면 다시 체크할 때 false로 변경
  const isCurrentlyLiked = heartImg.getAttribute('src') === '/img/heart.png';
  const newChecked = !isCurrentlyLiked;
  const url = `/daily/like?dailyArticleId=${dailyArticleId}&memberId=${memberId}&checked=${newChecked}`;
  // 서버에 false나 true 전달
  const object = await httpRequest(url);

  console.log(object);

  if (object.isUpdated) {
    heartImg.setAttribute('src', newChecked ? '/img/heart.png' : '/img/heart2.png');
    const heartCountArea = document.querySelector('#heartCount');
    heartCountArea.textContent = object.heartCount;
  }


}

function main() {
  eventRegister();
}

main();