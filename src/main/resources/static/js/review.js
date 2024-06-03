// 후기 데이터를 가져와서 동적으로 출력하는 함수
const fetchReviews = async function (placeId) {
    try {
        const response = await fetch(`/map/place/review/list?placeId=${placeId}`);
        const reviews = await response.json();

        // 후기 데이터가 없으면 함수 종료
        if (reviews.length === 0) {
            return;
        }

        // 후기 데이터를 동적으로 출력
        const reviewList = document.getElementById('place-review');
        reviewList.innerHTML = ''; // 기존 후기를 모두 지웁니다.

        reviews.forEach(review => {
            const reviewElement = document.createElement('div');
            reviewElement.classList.add('review');

            reviewElement.innerHTML = `
            <div>
                <img src="/img/profile.png">
            </div>
            <div>
                <ul>
                    <li class="review-writer">${review.memberId}</li>
                    <li class="review-content">${review.review}</li>
                </ul>
            </div>
        `;

            reviewList.appendChild(reviewElement);
        });
    } catch (error) {
        console.error('에러 발생:', error);
        alert('후기 데이터를 불러오는 데 실패했습니다.');
    }
};

// 폼 제출 시 후기 데이터를 추가하고 다시 출력합니다.
const handleSubmitButton = async function (event) {
    // 폼 제출을 방지합니다. 기본 제출 행동을 취소합니다.
    event.preventDefault();

    // 폼 데이터를 FormData 객체로 생성합니다.
    const formData = new FormData(document.querySelector('form'));

    try {
        // 장소 ID를 가져옵니다.
        const placeId = formData.get('placeId');

        const placeInfoResponse = await httpRequest('/map/place/review/info', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ placeId })
        });

        // 장소가 존재하지 않으면 신규 장소를 등록합니다.
        if (!placeInfoResponse) {
            const newPlaceResponse = await httpRequest('/map/place/review/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    placeId: formData.get('placeId'),
                    memberId: formData.get('memberId'),
                    nickname: formData.get('nickname'),
                    review: formData.get('review'),
                    placeName: formData.get('placeName'),
                    addressName: formData.get('addressName'),
                    roadAddressName: formData.get('roadAddressName'),
                    y: formData.get('y'),
                    x: formData.get('x')
                })
            });

            console.log('신규 장소 등록 결과:', newPlaceResponse);
            alert('신규 장소가 등록되었습니다.');
        } else {
            console.log('이미 존재하는 장소입니다:', placeInfoResponse);
            alert('해당 장소는 이미 존재합니다.');
        }

        // 후기 데이터를 다시 가져와서 출력합니다.
        await fetchReviews(placeId);
    } catch (error) {
        console.error('에러 발생:', error);
        alert('요청 처리 중 오류가 발생했습니다. 다시 시도해 주세요.');
    }
};

// Thymeleaf의 hidden input 요소에서 장소의 ID를 가져와서 JavaScript 변수에 할당
const placeId = document.getElementById('placeId').value;

// 페이지 로드 시 후기 데이터를 가져와서 출력하는 함수 호출
fetchReviews(placeId);

// 폼 제출 버튼에 이벤트 핸들러를 추가합니다.
document.querySelector('form').addEventListener('submit', handleSubmitButton);