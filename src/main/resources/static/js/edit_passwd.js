const httpRequest = function (url) {
    return fetch(url).then(response => response.json()).catch();
}

const eventRegister = function () {
    document.querySelector("#edit-oldPasswd").addEventListener("blur", handleChangeInputPasswd)
    document.querySelector("#edit-confirmPasswd").addEventListener("input", handleChangPasswd);
}

const handleChangeInputPasswd = async function (event) {
    let inputPasswd = event.target.value;
    const url = `/member/passwdCheck/${inputPasswd}`;
    let resultMessage = null;
    // 유효성 검증
    console.log(url);
    const object = await httpRequest(url);
    if (object.result) {
        resultMessage = `<span style="color: blue">${object.message}</span>`;
    } else {
        resultMessage = `<span style="color: red">${object.message}</span>`;
    }
    checkPasswdResult(resultMessage);
}


// 아이디 입력 관련 메시지 출력
const checkPasswdResult = function (message) {
    const resultView = document.querySelector("#confirmPasswdDup");
    if (resultView) {
        resultView.innerHTML = message;
    }
}

const handleChangPasswd = function (event){
    let inputPasswd = event.target.value;
    let resultMessage = null;

    const passwd = document.querySelector("#edit-newPasswd").value;

    if (passwd !== inputPasswd) {
        resultMessage = `<span style="color: red">비밀번호가 일치하지 않습니다.</span>`;
    }
    showPasswdResult(resultMessage);
}

// 비밀번호 값 동일여부 확인
const showPasswdResult = function (message) {
    const resultView = document.querySelector("#editPasswdDup");
    if (resultView) {
        resultView.innerHTML = message;
    }
}


/** entry point */
function main() {
    eventRegister();
}

main();