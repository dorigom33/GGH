package com.secu.team2.deal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.secu.team2.deal.service.DealInfoService;
import com.secu.team2.deal.vo.DealInfoRecordVO;
import com.secu.team2.deal.vo.DealInfoVO;
import com.secu.team2.user.vo.UserInfoVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DealInfoController {
	private final DealInfoService dealInfoService;

	@GetMapping("/select-deal-info-by-seller")
	public DealInfoVO selectDealInfoBySeller() { // 판매자 번호로 거래정보 조회
		return dealInfoService.selectDealInfoBySeller();
	}

	@GetMapping("/select-deal-info-by-buyer")
	public DealInfoVO selectDealInfoByBuyer() { // 구매자 번호로 거래정보 조회
		return dealInfoService.selectDealInfoByBuyer();
	}
	
	@GetMapping("/get-bi-num-and-bi-price/{diNum}")
	public DealInfoVO selectBiNumByDiNum(@PathVariable int diNum) {
	    return dealInfoService.selectBiNumByDiNum(diNum);
	}

	@GetMapping("/count-deal-info-by-di-stat")
	public int countDealInfoByDiStat(@AuthenticationPrincipal UserInfoVO user) {
		if(user != null) {
			return dealInfoService.countDealInfoByDiStat(user.getUiNum());
		}
		return 0;
	}
	
	@GetMapping("/get-deal-info-list-by-uinum/{uiNum}")
	public List<DealInfoRecordVO> getDealInfoListByUiNum(@PathVariable int uiNum){
		return dealInfoService.getDealInfoListByUiNum(uiNum);
	}

	@PostMapping("/insert-deal-info")
	public ResponseEntity<Map<String, Integer>> insertDealInfo(@RequestParam int ciNum) { // 거래정보 입력(수정 필요)
		int newDiNum = dealInfoService.insertDealInfoWithChatInfo(ciNum);

		Map<String, Integer> responseMap = new HashMap<>();
		responseMap.put("diNum", newDiNum);
		log.info("newDiNum=>{}", newDiNum);
		return ResponseEntity.ok(responseMap);
	}

	@PostMapping("/update-buyer-di-stat/{diNum}")
	public int updateBuyerDiStat(@PathVariable int diNum) {
		return dealInfoService.updateBuyerDiStat(diNum);
	}

}
