package com.secu.team2.deal.mapper;

import java.util.List;

import com.secu.team2.deal.vo.DealInfoRecordVO;
import com.secu.team2.deal.vo.DealInfoVO;

public interface DealInfoMapper {
	DealInfoVO SelectDealInfoBySeller(int sellerUiNum); // 판매자 번호로 거래정보 조회

	DealInfoVO SelectDealInfoByBuyer(int buyerUiNum); // 구매자 번호로 거래정보 조회
	
	DealInfoVO selectBiNumByDiNum(int diNum);
	
	List<DealInfoRecordVO> getDealInfoListByUiNum(int uiNum);

	int countDealInfoByDiStat(int buyerUiNum);

	int insertDealInfoWithChatInfo(DealInfoVO deal); // 거래정보 입력

	int updateBuyerDiStat(int diNum);
}
