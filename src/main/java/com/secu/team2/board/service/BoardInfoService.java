package com.secu.team2.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.secu.team2.board.mapper.BoardInfoMapper;
import com.secu.team2.board.vo.BoardInfoVO;
import com.secu.team2.chat.service.ChatInfoService;
import com.secu.team2.common.vo.ResponsePageVO;
import com.secu.team2.file.service.FileInfoService;
import com.secu.team2.file.vo.FileInfoVO;
import com.secu.team2.like.service.LikeInfoService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class BoardInfoService {

	private final BoardInfoMapper boardInfoMapper;
	private final FileInfoService fileInfoService;
	private final ChatInfoService chatInfoService;
	private final LikeInfoService likeInfoService;

	// 게시글 등록
	public int insertBoardInfo(BoardInfoVO board) {
	    int result = boardInfoMapper.insertBoardInfo(board);
	    board.setBiSort(Integer.valueOf(board.getBiNum()));
	    result += boardInfoMapper.updateBiSort(board);
	    result += fileInfoService.insertFileInfos(board.getBiNum(), board.getFiles());

	    return result;
	}


	// 게시글 리스트
	public ResponsePageVO<BoardInfoVO> selectBoardInfos(BoardInfoVO board){
		 board.setEnd(board.getPageSize());
		 int start = (board.getPage()-1) * board.getPageSize();
		 board.setStart(start);
		List<BoardInfoVO> boards = boardInfoMapper.selectBoardInfos(board);
		// 파일, 채팅 개수, 관심 개수
		for(BoardInfoVO b : boards) {
			b.setFiles(fileInfoService.selectFileInfos(b.getBiNum()));
			b.setChatCnt(chatInfoService.getChatInfoCntByBiNum(b.getBiNum()));
			b.setLikeCnt(likeInfoService.getLikeInfoCntByBiNum(b.getBiNum()));
			
		}
		ResponsePageVO<BoardInfoVO> resVO = new ResponsePageVO<>();
		resVO.setList(boards);
		resVO.setTotalCnt(boardInfoMapper.selectBoardInfoCnt(board));
		return resVO;
	}
	
	// 조회수 기준 게시물 리스트 (인기 상품)
	public List<BoardInfoVO> getHotBoardInfos(BoardInfoVO board){
		List<BoardInfoVO> boards = boardInfoMapper.selectBoardInfosByViews(board);
		// 파일, 채팅 개수, 관심 개수
		for(BoardInfoVO b : boards) {
			b.setFiles(fileInfoService.selectFileInfos(b.getBiNum()));
			b.setChatCnt(chatInfoService.getChatInfoCntByBiNum(b.getBiNum()));
			b.setLikeCnt(likeInfoService.getLikeInfoCntByBiNum(b.getBiNum()));
		}
		return boards;
	}

	// 게시글 뷰
	public BoardInfoVO selectBoardInfo(int biNum) {
		BoardInfoVO board = boardInfoMapper.selectBoardInfo(biNum);
		// 파일 가져오기
		List<FileInfoVO> files = fileInfoService.selectFileInfos(biNum);
		board.setFiles(files);
		
		// 채팅 개수, 관심목록 개수 가져오기
		board.setChatCnt(chatInfoService.getChatInfoCntByBiNum(biNum));
		board.setLikeCnt(likeInfoService.getLikeInfoCntByBiNum(biNum));
		
		// 조회수 늘리기
		boardInfoMapper.updateBoardInfoViewsCnt(biNum);
		return board;
	}

	// 게시글 수정
	public int updateBoardInfo(BoardInfoVO board) {
		int result = boardInfoMapper.updateBoardInfo(board);
		result += fileInfoService.updateFileInfos(board.getBiNum(), board.getFiles());
		return result;
	}

	// 게시글 상태 업데이트
    public int updateBiStat(int biNum) {
        return boardInfoMapper.updateBiStat(biNum);
    }

	// 게시글 삭제
	public int deleteBoardInfo(int biNum) {
		BoardInfoVO board = boardInfoMapper.selectBoardInfo(biNum);
		List<FileInfoVO> files = fileInfoService.selectFileInfos(biNum);
		board.setFiles(files);
		int result = fileInfoService.deleteFileInfos(biNum, board.getFiles());
		result += boardInfoMapper.deleteBoardInfo(biNum);
		return result;
	}

	// 게시글 active 변경 12.8
	public int updateBoardActive(int biNum) {
		return boardInfoMapper.updateBoardActive(biNum);
	}
	
	public int updateBoardPrice(int biNum, int oiPrice) {
        return boardInfoMapper.updateBoardPrice(biNum, oiPrice);
    }

	public int getSellerUiNumByBiNum(int biNum) {
        return boardInfoMapper.selectUiNumByBiNum(biNum);
    }

	// 관리자페이지에서 보드 리스트가져오기
	public List<BoardInfoVO> selectBoardInfoList(BoardInfoVO board){
		return boardInfoMapper.selectBoardInfoList(board);
	}
	// biSort biNum값과 같은 값으로 업데이트
	public int updateboardBiSort(BoardInfoVO board) {
		return boardInfoMapper.updateBiSort(board);
	}
	
	public int updateBoardInfoList(BoardInfoVO board) {
	    return boardInfoMapper.updateBoardInfoList(board);
	}

}