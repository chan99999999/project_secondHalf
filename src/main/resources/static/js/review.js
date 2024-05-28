window.addEventListener('DOMContentLoaded', function() {
    var writeReviewBtn = document.getElementById('write-review-btn');
    if (writeReviewBtn) {
        writeReviewBtn.addEventListener('click', function () {
            var reviewContent = document.getElementById('review-content').value;
            if (reviewContent.trim() !== '') {
                var xhr = new XMLHttpRequest();
                xhr.open('POST', '/api/review/add', true);
                xhr.setRequestHeader('Content-Type', 'application/json');
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === XMLHttpRequest.DONE) {
                        if (xhr.status === 200) {
                            // 등록 성공 시 페이지 새로고침
                            window.location.reload();
                        } else {
                            alert('후기 등록에 실패했습니다.');
                        }
                    }
                };
                // 후기 데이터를 JSON 형식으로 전송
                xhr.send(JSON.stringify({review: reviewContent}));
            } else {
                alert('후기를 입력해주세요.');
            }
        });
    }
});