package com.secu.team2.rank.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.secu.team2.board.service.BoardInfoService;
import com.secu.team2.deal.service.DealInfoService;
import com.secu.team2.rank.mapper.RankInfoMapper;
import com.secu.team2.rank.vo.RankInfoVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RankInfoService {

	private final RankInfoMapper rankInfoMapper;
	private final BoardInfoService boardService;
	private final DealInfoService dealInfoService;

	public int insertSellerRankInfo(RankInfoVO rank) {
		return rankInfoMapper.insertSellerRankInfo(rank);
	}

	public int insertBuyerRankInfo(RankInfoVO rank) {
		return rankInfoMapper.insertBuyerRankInfo(rank);
	}


	public float selectAverageRankInfo(int uiNum) {
		return rankInfoMapper.selectAverageRankInfo(uiNum);
	}


	public int selectCountRankInfo(int uiNum) {
		return rankInfoMapper.selectCountRankInfo(uiNum);
	}

	 public List<RankInfoVO> getReviewInfo(int uiNum){
	        return rankInfoMapper.selectCountRivewInfo(uiNum);
	    }

	}
