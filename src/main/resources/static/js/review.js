// URL을 통해 데이터를 가져오는 HTTP 요청을 수행하는 함수 정의
const httpRequest = function (url, options = {}) {
    // fetch API를 사용하여 URL에 대한 HTTP 요청을 수행하고, 응답을 JSON 형식으로 변환하여 반환합니다.
    return fetch(url, options).then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        return response.json();
    });
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
const handleSubmitButton = async function (event) {
    // 폼 제출을 방지합니다. 기본 제출 행동을 취소합니다.
    event.preventDefault();

    // 폼 데이터를 FormData 객체로 생성합니다.
    const formData = new FormData(document.querySelector('form'));

    try {
        // placeId로 장소를 검색합니다.
        const placeId = formData.get('placeId');
        const placeInfoResponse = await httpRequest('/place/info', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ placeId })
        });

        // 장소가 존재하지 않으면 신규 장소를 등록합니다.
        if (!placeInfoResponse) {
            const newPlaceResponse = await httpRequest('/place/review', {
                method: 'POST',
                body: formData
            });

            console.log('신규 장소 등록 결과:', newPlaceResponse);
            alert('신규 장소가 등록되었습니다.');
        } else {
            console.log('이미 존재하는 장소입니다:', placeInfoResponse);
            alert('해당 장소는 이미 존재합니다.');
        }
    } catch (error) {
        console.error('에러 발생:', error);
        alert('요청 처리 중 오류가 발생했습니다. 다시 시도해 주세요.');
    }
}

/** entry point */
function main() {
    // 이벤트 핸들러를 등록합니다.
    eventRegister();
}

// DOM이 완전히 로드된 후에 main 함수를 호출합니다.
window.onload = main;