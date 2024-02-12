/**
 * 시간, 금액, 주소 등 데이터를 보기 좋게 처리하는 함수 파일
 */

// 마지막 수정을 현재 시간 기준으로 계산해주는 함수
function calculateTime(lmodat, lmotim) {
    try {
        const curDate = new Date();
        const lmoDate = new Date(`${lmodat.slice(0, 4)}-${lmodat.slice(4, 6)}-${lmodat.slice(6)}T${lmotim.slice(0, 2)}:${lmotim.slice(2, 4)}:${lmotim.slice(4)}`);
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

 // 금액 천단위 콤마로 끊는 함수
function priceToString(price) {
    return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

// 지번 주소에서 '동'이 처음으로 나오는 위치를 찾는 함수
function findDong(addr){
	let index = addr.indexOf('동 '); 
	if(index !== -1){
		addr = addr.substring(0, index + 1);
	}
	// 없으면 그냥 리턴
	return addr;
}

function findSpace(addr){
	let index = addr.indexOf(' ');
	index = addr.indexOf(' ', index + 1);
	return index;
}

// 회원가입 시 전화번호 자동 하이픈 변환해주는 함수
const hypenTel = (target) => {
 target.value = target.value
   .replace(/[^0-9]/g, '')
   .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
}

// 금액 입력 시 자동으로 콤마를 넣어주는 함수
function inputNumberFormat(obj) {
	obj.value = comma(uncomma(obj.value));
}

function comma(str) {
	str = String(str);
	return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

function uncomma(str) {
	str = String(str);
	return str.replace(/[^\d]+/g, '');
}

// 클릭 시 페이지 맨 위로 이동하는 함수

// 애니메이션 효과 추가
function backToTopSmooth() {
	const position = document.documentElement.scrollTop || document.body.scrollTop;
	if (position) {
		window.requestAnimationFrame(() => {
			window.scrollTo(0, position - position / 10);
			backToTopSmooth();
		});
	}
}

