let checkChatInfo;
let checkDealInfo;
async function load(){
	const checkChatInfoRes = await fetch('/count-new-message-chat-room');
	checkChatInfo = await checkChatInfoRes.json();
	const checkDealInfoRes = await fetch('/count-deal-info-by-di-stat');
	checkDealInfo = await checkDealInfoRes.json();
	if(checkChatInfo > 0){
		document.querySelector('#chatAlertCnt').style.display = 'block';
	}
	if(document.querySelector('#chatAlertCnt') != undefined){
		if(checkChatInfo+checkDealInfo<100){
			document.querySelector('#chatAlertCnt').innerHTML = checkChatInfo+checkDealInfo;
		}else{
			document.querySelector('#chatAlertCnt').innerHTML = '99';
		}
	}
}
window.addEventListener('load', async function(){
	await load();
})