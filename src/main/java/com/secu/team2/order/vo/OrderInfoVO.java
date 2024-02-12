package com.secu.team2.order.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderInfoVO {
	private int oiNum; // 주문번호
	private int diNum; // 거래번호
	private int biNum; // 상품번호
	private int sellerUiNum; // 판매자번호
	private int buyerUiNum; // 구매자번호
	private String biName; // 상품이름
	private String oiPrice; // 구매한 가격
	private String credat; // 생성일자
	private String cretim; // 생성시간
}
