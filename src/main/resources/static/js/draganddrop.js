const eventRegister2 = function () {
  const dragArea = document.querySelector('#daily-register-img-wrap');

  // 드래그 앤 드롭 이벤트 리스너 등록
  dragArea.addEventListener("dragover", handleDragOver);
  dragArea.addEventListener("drop", handleDrop);
}

// 드래그 오버 이벤트 핸들러
const handleDragOver = function (event) {
  event.preventDefault();
}

// 드롭 이벤트 핸들러
const handleDrop = function (event) {
  event.preventDefault();

  const files = event.dataTransfer.files;
  const previewArea = document.querySelector('#daily-register-img-wrap');

  Array.from(previewArea.querySelectorAll('img.daily-register-img')).forEach(img => img.remove());

  
  Array.from(files).forEach(file => {
    if (!file.type.startsWith('image/')) return; // 이미지 파일이 아니면 건너뜀
    
    // 드롭된 파일들에 대한 미리보기 생성
    const reader = new FileReader();
    reader.onload = function (event) {
      const img = document.createElement('img');
      img.src = event.target.result;
      img.classList.add('daily-register-img');
      previewArea.insertBefore(img, previewArea.querySelector('#daily-register-img-upload'));

      // 파일 인풋 요소에 파일 추가
      const fileInput = document.querySelector('#file');
      fileInput.files = files
      console.log(files);



    };
    reader.readAsDataURL(file);
  });
}

function main() {
  eventRegister2();
}

main();
