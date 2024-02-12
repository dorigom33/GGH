package com.secu.team2.rank.mapper;

import java.util.List;

import com.secu.team2.deal.vo.DealInfoVO;
import com.secu.team2.rank.vo.RankInfoVO;

public interface RankInfoMapper {

	int insertSellerRankInfo(RankInfoVO rank);
	int insertBuyerRankInfo(RankInfoVO rank);
	float selectAverageRankInfo(int uiNum);
	int selectCountRankInfo(int uiNum);
	RankInfoVO selectRankInfoByDiNumAndUiNum(DealInfoVO deal);
	List<RankInfoVO> selectCountRivewInfo(int uiNum);
}
