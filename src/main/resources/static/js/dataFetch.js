function onMessage(socketMessage) {
    if (socketMessage.data !== 'refresh') return;
    $.ajax({
        url: `${location.pathname}/reload`,
        method: "GET",
        cache: false
    }).done(fragment => $("#target-reload").replaceWith(fragment))
}

const sendAlimTalk = (memberId, waitingId) => {
    if (!window.confirm("입장 메시지를 보낼까요?")) return;

    $.ajax({
        url: `/waiting/notice/${memberId}/${waitingId}`,
        method: "POST"
    }).done(res => alert("전송 완료"))
        .fail(data => alert("전송 실패"))
}

const deleteWaiting = (memberId, waitingId) => {
    if (!window.confirm("입장이 완료되었나요?")) return

    $.ajax({
        url: `/waiting/${memberId}/delete/${waitingId}`,
        method: "DELETE",
        cache: false
    }).done(fragment => {
        $("#target-reload").replaceWith(fragment)
        webSocket.send("refresh");
        alert("삭제 완료")
    }).fail(data => alert("삭제 실패"))
}

const cancelWaiting = (waitingNumber) => {
    const cancelWaitingNumberInput = document.getElementById("cancelWaitingNumber").value;
    if (parseInt(cancelWaitingNumberInput) !== waitingNumber) {
        alert("잘못 입력하셨습니다.");
        return
    }

    $.ajax({
        url: location.pathname,
        method: "DELETE",
    }).done(data => {
        webSocket.send("refresh");
        alert("대기 취소가 완료되었습니다.")
        location.href = `kakaotalk://inappbrowser/close`
    }).fail(data => alert("대기 취소에 실패하였습니다."))
}
