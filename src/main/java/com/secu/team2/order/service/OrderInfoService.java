package com.secu.team2.order.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.secu.team2.order.mapper.OrderInfoMapper;
import com.secu.team2.order.vo.OrderInfoVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderInfoService {
	private final OrderInfoMapper orderInfoMapper;

	public List<OrderInfoVO> selectOrderInfoBySellerUiNum(int sellerUiNum) { // 판매자번호로 주문정보 조회
		return orderInfoMapper.selectOrderInfoBySellerUiNum(sellerUiNum);
	}

	public List<OrderInfoVO> selectOrderInfoByBuyerUiNum(int buyerUiNum) { // 구매자번호로 주문정보 조회
		return orderInfoMapper.selectOrderInfoByBuyerUiNum(buyerUiNum);
	}

	public OrderInfoVO selectSellerUiNumByDiNum(int diNum) { // 주문번호, 거래번호, 판매자번호 조회
		return orderInfoMapper.selectSellerUiNumByDiNum(diNum);
	}

	public OrderInfoVO selectBuyerUiNumByDiNum(int diNum) { // 주문번호, 거래번호, 구매자번호 조회
		return orderInfoMapper.selectBuyerUiNumByDiNum(diNum);
	}

	public int insertOrderInfo(OrderInfoVO order) { // 주문정보 입력
		return orderInfoMapper.insertOrderInfo(order);
	}

	public int updateOrderInfo(OrderInfoVO order) { // 구매가격 입력
		return orderInfoMapper.updateOrderInfo(order);
	}
}
