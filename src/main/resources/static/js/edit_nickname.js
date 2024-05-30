const httpRequest = function (url) {
    return fetch(url).then(response => response.json()).catch();
}

const eventRegister = function () {
    document.querySelector("#edit-nickname").addEventListener("input", handleChangeInputNickname);
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


// 닉네임 입력 관련 메세지 출력
const showNicknameResult = function (message) {
    const resultView = document.querySelector("#editNicknameDup");
    if (resultView) {
        resultView.innerHTML = message;
    }
}

/** entry point */
function main() {
    eventRegister();
}

main();