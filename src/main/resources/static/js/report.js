//신고받는 상대방의 uiNum 전달
async function reportUser(uiNum){
	const ruiDesc = document.querySelector('#ruiDesc').value;
		if(ruiDesc.length > 80){
			alert('최대 80자 까지만 작성 가능합니다.');
			return;
		}
		if(ruiDesc != null){
			const report = JSON.stringify({
				ruiDesc : ruiDesc,
				targetUiNum : uiNum
			});
			
			const reportUserRes = await fetch('/insert-report-info',{
				method: 'POST',
				body: report,
				headers: {
					'Content-type':'application/json;charset=UTF-8'
				}
			});
					
			const reportUser = await reportUserRes.json();
			console.log(reportUser);
			
			if(reportUserRes.ok){
				alert('사용자를 신고했습니다.');
			}else{
				alert('잘못된 접근입니다.');
			}
		}
}