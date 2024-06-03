const httpRequest = function (url) {
    return fetch(url).then(response => response.json()).catch();
}

const eventRegister = function () {
    document.querySelector("#signup-id").addEventListener("input", handleChangeInputId);
    document.querySelector("#signup-nickname").addEventListener("input", handleChangeInputNickname);
    document.querySelector("#signup-pw-confirm").addEventListener("blur", handleChangPasswd);
}

const handleChangeInputId = async function (event) {
    let inputId = event.target.value;
    const url = `/member/idcheck/${inputId}`;
    let resultMessage = null;
    // 유효성 검증
    console.log(url);
    const object = await httpRequest(url);
    if (object.result) {
        resultMessage = `<span style="color: blue">${object.message}</span>`;
    } else {
        resultMessage = `<span style="color: red">${object.message}</span>`;
    }
    showIdResult(resultMessage);
}

const handleChangeInputNickname = async function (event) {
    let inputNickname = event.target.value;
    const url = `/member/nicknameCheck/${inputNickname}`;
    let resultMessage = null;

    const object = await httpRequest(url);
    if (object.result) {
        resultMessage = `<span style="color: blue">${object.message}</span>`;
    } else {
        resultMessage = `<span style="color: red">${object.message}</span>`;
    }
    showNicknameResult(resultMessage);
}

// 아이디 입력 관련 메시지 출력
const showIdResult = function (message) {
    const resultView = document.querySelector("#dupIdResult");
    if (resultView) {
        resultView.innerHTML = message;
    }
}

// 닉네임 입력 관련 메세지 출력
const showNicknameResult = function (message) {
    const resultView = document.querySelector("#dupNicknameResult");
    if (resultView) {
        resultView.innerHTML = message;
    }
}

const handleChangPasswd = function (event){
    let inputPasswd = event.target.value;
    let resultMessage = null;

    const passwd = document.querySelector("#signup-pw").value;

    if (passwd !== inputPasswd) {
        resultMessage = `<span style="color: red">비밀번호가 일치하지 않습니다.</span>`;
    }
    showPasswdResult(resultMessage);
}

// 비밀번호 값 동일여부 확인
const showPasswdResult = function (message) {
    const resultView = document.querySelector("#dupPasswdResult");
    if (resultView) {
        resultView.innerHTML = message;
    }
}


/** entry point */
function main() {
    eventRegister();
}

main();