package com.secu.team2.deal.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DealInfoVO {
	private int diNum; // 거래번호
	private int biNum; // 상품번호
	private int ciNum; // 채팅번호
	private int sellerUiNum; // 판매자번호
	private int buyerUiNum; // 구매자번호
	private String credat; // 생성일
	private String cretim; // 생성시간
	private String biTitle; // 상품제목
	private String biName; // 상품이름
	private String biPrice; // 상품가격
	private String biLoca; // 거래희망주소
	private String lmodat; // 최종수정일자
	private String lmotim; // 최종수정시간
}
