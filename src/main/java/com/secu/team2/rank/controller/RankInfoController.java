package com.secu.team2.rank.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.secu.team2.deal.service.DealInfoService;
import com.secu.team2.rank.service.RankInfoService;
import com.secu.team2.rank.vo.RankInfoVO;
import com.secu.team2.user.vo.UserInfoVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RankInfoController {

private final RankInfoService rankInfoService;
private final DealInfoService dealInfoService;


@PostMapping("/add-seller/{sellerUiNum}")
public int insertSellerUiNumInfo(@RequestBody RankInfoVO rank) {
    return rankInfoService.insertSellerRankInfo(rank);
}

@PostMapping("/add-buyer/{buyerUiNum}")
public int insertBuyerUiNumInfo(@RequestBody RankInfoVO rank) {
	return rankInfoService.insertBuyerRankInfo(rank);
}

@GetMapping("/select/{uiNum}")
public float selectAverageRankInfo(@PathVariable int uiNum){
   return rankInfoService.selectAverageRankInfo(uiNum);
}

@GetMapping("/count/{uiNum}")
public int selectCountRankInfo(@PathVariable int uiNum){
   return rankInfoService.selectCountRankInfo(uiNum);
}

@GetMapping("/review/{uiNum}")
public List<RankInfoVO> getReviewInfo(@PathVariable int uiNum){
   return rankInfoService.getReviewInfo(uiNum);

}

@GetMapping("/num")
public Map<String, Integer> selectUiNum(@AuthenticationPrincipal UserInfoVO userDetails) {
    int uiNum = userDetails.getUiNum();
    Map<String, Integer> response = new HashMap<>();
    response.put("uiNum", uiNum);
    return response;
}

}