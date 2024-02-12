package com.secu.team2.order.mapper;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.secu.team2.order.vo.OrderInfoVO;

public interface OrderInfoMapper {
	List<OrderInfoVO> selectOrderInfoBySellerUiNum(@Param("sellerUiNum") int sellerUiNum); // 판매자번호로 주문정보 조회

	List<OrderInfoVO> selectOrderInfoByBuyerUiNum(@Param("buyerUiNum") int buyerUiNum); // 구매자번호로 주문정보 조회

	OrderInfoVO selectSellerUiNumByDiNum(int diNum); // 주문번호, 거래번호, 판매자번호 조회

	OrderInfoVO selectBuyerUiNumByDiNum(int diNum); // 주문번호, 거래번호, 구매자번호 조회

	int insertOrderInfo(OrderInfoVO order); // 주문정보 입력

	int updateOrderInfo(OrderInfoVO order); // 구매가격 입력
}