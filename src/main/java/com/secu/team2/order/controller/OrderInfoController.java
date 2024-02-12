package com.secu.team2.order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.secu.team2.order.service.OrderInfoService;
import com.secu.team2.order.vo.OrderInfoVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderInfoController {
	private final OrderInfoService orderInfoService;

	@GetMapping("/get-orderInfo/seller/{sellerUiNum}")
	public List<OrderInfoVO> getOrderInfoVOBySellerUiNum(@PathVariable int sellerUiNum) { // 구매자번호로 주문정보 조회
		return orderInfoService.selectOrderInfoBySellerUiNum(sellerUiNum);
	}

	@GetMapping("/get-orderInfo/buyer/{buyerUiNum}")
	public List<OrderInfoVO> getOrderInfoVOByBuyerUiNum(@PathVariable int buyerUiNum) { // 판매자번호로 주문정보 조회
		return orderInfoService.selectOrderInfoByBuyerUiNum(buyerUiNum);
	}

	@GetMapping("/fetch-seller-num")
	public Map<String, Object> fetchSellerUiNum(@RequestParam int diNum) { // 평점 입력을 위해 주문번호, 거래번호, 판매자 번호 추출 후 반환하는
																			// 컨트롤러
		OrderInfoVO orderInfo = orderInfoService.selectSellerUiNumByDiNum(diNum);

		Map<String, Object> result = new HashMap<>(); // result 변수에 주문번호, 거래번호, 판매자번호를 입력
		result.put("oiNum", orderInfo.getOiNum());
		result.put("diNum", orderInfo.getDiNum());
		result.put("sellerUiNum", orderInfo.getSellerUiNum());

		return result; // result 반환
	}

	@GetMapping("/fetch-buyer-num")
	public Map<String, Object> fetchBuyerUiNum(@RequestParam int diNum) {// 평점 입력을 위해 주문번호, 거래번호, 구매자 번호 추출 후 반환하는 컨트롤러
		OrderInfoVO orderInfo = orderInfoService.selectBuyerUiNumByDiNum(diNum);

		Map<String, Object> result = new HashMap<>();// result 변수에 주문번호, 거래번호, 구매자번호를 입력
		result.put("oiNum", orderInfo.getOiNum());
		result.put("diNum", orderInfo.getDiNum());
		result.put("buyerUiNum", orderInfo.getBuyerUiNum());

		return result; // result 반환

	}

	@PostMapping("/add-order-info")
	@Transactional
	public int insertOrderInfo(@RequestBody OrderInfoVO orderInfo) { // 주문정보 입력(수정 필요)
		return orderInfoService.insertOrderInfo(orderInfo);
	}

	@PostMapping("/update-order-info")
	public int updateOrderInfo(@RequestBody OrderInfoVO orderInfo) { // 주문가격 입력
		return orderInfoService.updateOrderInfo(orderInfo);
	}
}
