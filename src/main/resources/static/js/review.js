// review.js

// URL을 통해 데이터를 가져오는 HTTP 요청을 수행하는 함수 정의
const httpRequest = function (url) {
    // fetch API를 사용하여 URL에 대한 HTTP 요청을 수행하고, 응답을 JSON 형식으로 변환하여 반환합니다.
    return fetch(url).then(response => response.json());
}

/** 이벤트 타겟에 이벤트 핸들러 연결(등록) */
const eventRegister = function () {
    // id가 "addBtn"인 요소를 찾습니다.
    const addButton = document.querySelector("#addBtn");
    if (addButton) {
        // 만약 해당 요소가 존재하면, "click" 이벤트가 발생했을 때 handleSubmitButton 함수를 호출합니다.
        addButton.addEventListener("click", handleSubmitButton);
    } else {
        // 요소를 찾지 못한 경우 에러 메시지를 출력합니다.
        console.error("addBtn 요소를 찾을 수 없습니다.");
    }
}

// 리뷰 등록 이벤트 처리
const handleSubmitButton = function (event) {
    // 폼 제출을 방지합니다. 기본 제출 행동을 취소합니다.
    event.preventDefault();
    // "reviewForm" 폼에서 'review' 필드의 값을 가져옵니다.
    const review = document.reviewForm.review.value;
    const placeId = document.reviewForm.placeId.value;
    const nickname = document.reviewForm.nickname.value;

    // 콘솔에 후기 메시지를 출력합니다.
    console.log(review);

    // 입력 데이터 형식 검사 등 추가 로직이 필요할 경우 여기에 추가합니다.
    // 서버로 폼 데이터를 제출합니다.
    document.reviewForm.submit();
}

/** entry point */
function main() {
    // 이벤트 핸들러를 등록합니다.
    eventRegister();
}

// DOM이 완전히 로드된 후에 main 함수를 호출합니다.
window.onload = main;