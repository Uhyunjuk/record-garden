document.addEventListener("DOMContentLoaded", function () {
    console.log("JS 실행됨");

    const form = document.querySelector("#diaryForm");
    const writerInput = document.getElementById("diaryWriter");
    const contentsInput = document.getElementById("diaryContents");
    const moodInput = document.getElementById("moodToday");

    form.addEventListener("submit", function (event) {
        event.preventDefault(); // 새로고침 방지

        const diaryWriter = writerInput.value.trim();
        const diaryContents = contentsInput.value.trim();
        const moodToday = moodInput.value.trim();

        // 오늘의 감정을 이모지만 남기도록 변경
        const moodMap = {
            "happy": "😊",
            "sad": "😢",
            "angry": "😡",
            "excited": "🤩",
            "tired": "😴",
            "calm": "😌",
            "soso": "🙂"
        };
        const moodEmoji = moodMap[moodToday] || "";

        form.innerHTML = `
            <div class="d-flex align-items-center gap-1 mb-3">
                <label class="form-label fw-bold mb-0" style="min-width: 50px;">작성자</label>
                <input type="text" class="form-control" value="${diaryWriter}" readonly>
            </div>
            <div class="d-flex align-items-center gap-1 mb-3">
                <label class="form-label fw-bold mb-0" style="min-width: 50px;">내용</label>
                <textarea class="form-control" rows="2" readonly>${diaryContents}</textarea>
            </div>
            <div class="d-flex align-items-center gap-1 mb-3">
                <label class="form-label fw-bold mb-0" style="min-width: 50px;">감정</label>
                <input type="text" class="form-control" value="${moodEmoji}" readonly>
            </div>
            <div class="d-flex justify-content-around">
                <button id="modifyDiary" class="btn btn-primary">수정하기</button>
                <button id="listDiary" class="btn btn-primary">목록보기</button>
            </div>
            <hr class="my-3">
            <small class="text-muted">로그인을 하면 한 줄 일기를 수정하고 관리할 수 있어요!</small>
        `;

        // 버튼 클릭시 알림 띄우기
        document.getElementById("modifyDiary").addEventListener("click", function () {
            alert("로그인이 필요합니다.");
        });

        document.getElementById("listDiary").addEventListener("click", function () {
            alert("로그인이 필요합니다.");
        });
    });
});