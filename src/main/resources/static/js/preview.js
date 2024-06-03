const eventRegister = function () {
  const fileInput = document.querySelector('#file');
  fileInput.addEventListener("change", previewImages);
}

const previewImages = function(event) {
  const files = event.target.files;
  const previewArea = document.querySelector('#daily-register-img-wrap');  // 전체 래퍼 선택

  // 기존 미리보기 이미지 제거 (file input을 제외한 이미지 제거)
  Array.from(previewArea.querySelectorAll('img.daily-register-img')).forEach(img => img.remove());

  Array.from(files).forEach(file => {
    if (!file.type.startsWith('image/')) return; // 이미지 파일이 아니면 종료

    const reader = new FileReader();
    reader.onload = function(event) {
      const img = document.createElement('img');
      img.src = event.target.result;
      img.classList.add('daily-register-img');
      previewArea.insertBefore(img, previewArea.querySelector('#daily-register-img-upload'));
    };
    reader.readAsDataURL(file);
  });
};

function main() {
  eventRegister();
}

main();
