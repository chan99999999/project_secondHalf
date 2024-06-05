// 후기 데이터를 가져와서 동적으로 출력하는 함수
const fetchReviews = async function (placeId) {
    try {
        const response = await fetch(`/map/place/review/list?placeId=${placeId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        });

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
                    <img src="/img/profile.png" alt="Profile Image">
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
    event.preventDefault();

    const formData = new FormData(document.querySelector('form'));
    const placeId = formData.get('placeId');

    try {
        const placeInfoResponse = await fetch('/map/place/review/info', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(Object.fromEntries(formData))
        });

        if (!placeInfoResponse.ok) {
            const newPlaceResponse = await fetch('/map/place/review/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(Object.fromEntries(formData))
            });

            console.log('신규 장소 등록 결과:', newPlaceResponse);
        } else {
            console.log('이미 존재하는 장소입니다:', placeInfoResponse);
        }

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