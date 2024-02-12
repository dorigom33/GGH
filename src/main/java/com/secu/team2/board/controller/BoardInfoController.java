package com.secu.team2.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.secu.team2.board.service.BoardInfoService;
import com.secu.team2.board.vo.BoardInfoVO;
import com.secu.team2.common.vo.ResponsePageVO;
import com.secu.team2.like.service.LikeInfoService;
import com.secu.team2.like.vo.LikeInfoVO;
import com.secu.team2.user.vo.UserInfoVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BoardInfoController {

	private final BoardInfoService boardInfoService;
	private final LikeInfoService likeInfoService;

	// 생성자
	public BoardInfoController(@Autowired BoardInfoService boardInfoService, @Autowired LikeInfoService likeInfoService) {
		this.boardInfoService = boardInfoService;
		this.likeInfoService = likeInfoService;
	}

	// 게시글 리스트
	@GetMapping("/board-infos")
	public ResponsePageVO<BoardInfoVO> getBoardInfos(BoardInfoVO board){
		return boardInfoService.selectBoardInfos(board);
	}

	// 게시글 뷰
	@GetMapping("/board-infos/{biNum}")
	public BoardInfoVO getGoodsInfo(@PathVariable int biNum, @AuthenticationPrincipal UserInfoVO user){
		BoardInfoVO board = boardInfoService.selectBoardInfo(biNum);
		// 해당 게시물이 관심목록에 있는지 확인
		LikeInfoVO like = new LikeInfoVO();
		like.setBiNum(biNum);
		like.setUiNum(user.getUiNum());
		int isLike = likeInfoService.SelectLikeInfoByBiNum(like);
		board.setLike(false);
		if(isLike >= 1) {
			board.setLike(true);
		}
		// 게시물 작성자인지 확인
		board.setAuthor(false);
		if(user != null && user.getUiNum() == board.getUiNum()){
			board.setAuthor(true);
		}
		return board;
	}

	// 게시글 등록
	@PostMapping("/board-infos")
	public int addBoardInfos(BoardInfoVO board, @AuthenticationPrincipal UserInfoVO user) {
		board.setBiPrice(board.getBiPrice().replace(",", ""));
		board.setUiNum(user.getUiNum());
		return boardInfoService.insertBoardInfo(board);
	}

	// 게시글 수정
	@PatchMapping("/board-infos/{biNum}")
	public int modifyBoardInfos(BoardInfoVO board) {
		board.setBiPrice(board.getBiPrice().replace(",", ""));
		log.info("controller board=>{}", board);
		return boardInfoService.updateBoardInfo(board);
	}


	//게시글 active 변경 12.8
	@PatchMapping("/board-infoList/{biNum}")
	public int updateBoardActive(@PathVariable int biNum) {
		return boardInfoService.updateBoardActive(biNum);
	}
	
	@PatchMapping("/board-infos/update-bi-price/{biNum}/{oiPrice}")
	public int updateBoardPrice(@PathVariable("biNum") int biNum, @PathVariable("oiPrice") int oiPrice) {
	    return boardInfoService.updateBoardPrice(biNum, oiPrice);
	}


	// 게시물 삭제
	@DeleteMapping("/board-infos/{biNum}")
	public int dropBoardInfo(@PathVariable int biNum) {
		log.info("biNum=>{}", biNum);
		return boardInfoService.deleteBoardInfo(biNum);
	}

	@GetMapping("/board-infoList")
	public List<BoardInfoVO> selectBoardInfoList(BoardInfoVO board){
		return boardInfoService.selectBoardInfoList(board);
	}
	
	// 조회수 기준 게시물 리스트 (인기 상품)
	@GetMapping("/board-infos-hot")
	public List<BoardInfoVO> getHotBoardInfos(BoardInfoVO board){
		return boardInfoService.getHotBoardInfos(board);
	}
	
	@PatchMapping("/board-info-update-biSort/{biNum}")
	public int updateBoardInfoBiSort(BoardInfoVO board) {
		return boardInfoService.updateboardBiSort(board);
	}
	
	@PatchMapping("/board-info-update-BoardInfoList/{biNum}")
	public int updateBoardInfoList(BoardInfoVO board) {
		board.setBiPrice(board.getBiPrice().replace(",", ""));
		return boardInfoService.updateBoardInfoList(board);
	}
	
	// 판매 상태 업데이트
	@PatchMapping("/board-infos/update-bi-stat/{biNum}")
	public int updateBiStat(@PathVariable int biNum) {
		return boardInfoService.updateBiStat(biNum);
	}
}