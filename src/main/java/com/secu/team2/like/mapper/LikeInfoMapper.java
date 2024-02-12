package com.secu.team2.like.mapper;

import java.util.List;

import com.secu.team2.like.vo.LikeInfoVO;

public interface LikeInfoMapper {
	int insertLikeInfo(LikeInfoVO like); // 관심 표시할 때 추가
	int deleteLikeInfo(LikeInfoVO like); // 관심 해제할 때 삭제
	List<LikeInfoVO> selectLikeInfos(LikeInfoVO like); // 관심 목록 리스트
	int selectLikeInfoByBiNum(LikeInfoVO like); // 게시글이 관심 목록에 있는지 확인
	int selectLikeInfoCnt(LikeInfoVO like); //관심 목록 게시물 개수
	int selectLikeInfoCntByBiNum(int biNum); // 해당 게시물의 관심 목록 개수
}