package com.secu.team2.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.secu.team2.board.vo.BoardInfoVO;

public interface BoardInfoMapper {
	int selectUiNumByBiNum(int biNum);
	int insertBoardInfo(BoardInfoVO board); // 게시물 등록
	List<BoardInfoVO> selectBoardInfos(BoardInfoVO board); // 게시물 리스트
	int selectBoardInfoCnt(BoardInfoVO board); // 전체 게시물 개수
	BoardInfoVO selectBoardInfo(int biNum); // 게시물 뷰
	String selectBiTitleByBiNum(int biNum); // 게시물 이름
	int updateBoardInfo(BoardInfoVO board); // 게시물 수정
	int updateBiStat(int biNum);
	int updateBoardActive(int biNum); //게시글 active 변경 12.8
	int updateBoardPrice(@Param("biNum") int biNum, @Param("oiPrice") int oiPrice); //게시글 가격 변경
	int deleteBoardInfo(int biNum); // 게시물 삭제
	List<BoardInfoVO>selectBoardInfoList(BoardInfoVO board);
	int updateBoardInfoViewsCnt(int biNum); // 조회수 늘리기
	List<BoardInfoVO> selectBoardInfosByViews(BoardInfoVO board); // 조회수 기준 게시물 리스트 (인기 상품)
	int updateBiSort(BoardInfoVO board);
	int updateBoardInfoList(BoardInfoVO board);
}
