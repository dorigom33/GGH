// 마지막 수정을 현재 시간 기준으로 계산해주는 함수
function calculateTime(credat, cretim) {
    try {
        const curDate = new Date();
        const lmoDate = new Date(`${credat.slice(0, 4)}-${credat.slice(4, 6)}-${credat.slice(6)}T${cretim.slice(0, 2)}:${cretim.slice(2, 4)}:${cretim.slice(4)}`);
        const timeDiff = curDate - lmoDate;
        const sec = Math.floor(timeDiff / 1000);
        const min = Math.floor(sec / 60);
        const hours = Math.floor(min / 60);
        const days = Math.floor(hours / 24);
        const mon = Math.floor(days / 30);

        if(mon > 0){
			return mon + "달 전";
        }else if (days > 0) {
			return days + "일 전";
        }else if (hours > 0) {
			return hours + "시간 전";
        }else if (min > 0) {
			return min + "분 전";
        }else {
			return "방금 전";
        }
    }catch (e) {
		console.error(e);
		return "날짜 계산 오류";
    }
}