package com.secu.team2.board.vo;

import java.util.List;

import com.secu.team2.file.vo.FileInfoVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardInfoVO {
	private int biNum;
	private int uiNum;
	private String biTitle;
	private String biName;
	private String biPrice;
	private String biContent;
	private String biLoca;
	private String biAddr;
	private String biStat;
	private String uiName;
	private String credat;
	private String cretim;
	private String lmodat;
	private String lmotim;
	private String active;
	private int biSort;
	private int biCount;
	private List<FileInfoVO> files;
	private int chatCnt; // 게시글의 채팅방 개수
	private int likeCnt; // 게시글의 관심 목록 개수
	private int biViews; // 조회수
	private String uiImgName; // 프사 이름
	private String uiImgPath; // 프사 경로
	private boolean isAuthor; // 게시물 작성자인지 여부
	private boolean isLike; // 게시글이 관심 목록에 있는지 여부
	private String searchNum; // 유저 번호로 검색
	private String search; // 검색 내용
	private String sido; // 시도 검색
	private String gungu; // 군구 검색
	private int page; // 현재 페이지
	private int start; // 시작 번호
	private int end; // 끝 번호
	private int pageSize; // 보여질 게시물의 개수 (한페이지 크기)
	private String sorting; // 정렬
	private String userYear; // 유저 생성 년도
	private String userMonth; // 유저 생성 달
}