package com.secu.team2.like.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.secu.team2.common.vo.ResponsePageVO;
import com.secu.team2.like.service.LikeInfoService;
import com.secu.team2.like.vo.LikeInfoVO;
import com.secu.team2.user.vo.UserInfoVO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
public class LikeInfoController {
	private final LikeInfoService likeInfoService;

	// 관심 등록
	@PostMapping("/like-infos/{biNum}")
	public int addLikeInfo(LikeInfoVO like, @PathVariable int biNum, @AuthenticationPrincipal UserInfoVO user) {
		like.setBiNum(biNum);
		like.setUiNum(user.getUiNum());
		log.info("like=>{}", like);
		return likeInfoService.insertLikeInfo(like);
	}

	// 관심 취소
	@DeleteMapping("/like-infos/{biNum}")
	public int dropLikeInfo(LikeInfoVO like, @PathVariable int biNum, @AuthenticationPrincipal UserInfoVO user) {
		like.setBiNum(biNum);
		like.setUiNum(user.getUiNum());
		log.info("like=>{}", like);
		return likeInfoService.deleteLikeInfo(like);
	}

	// 관심 목록 리스트
	@GetMapping("/like-infos")
	public ResponsePageVO<LikeInfoVO> getLikeInfos(LikeInfoVO like, @AuthenticationPrincipal UserInfoVO user){
		like.setUiNum(user.getUiNum());
		log.info("like=>{}", like);
		return likeInfoService.selectLikeInfos(like);
	}

	// 해당 게시물이 관심 목록에 있는지 확인
	@GetMapping("/like-infos/{biNum}")
	public int getLikeInfo(LikeInfoVO like, @PathVariable int biNum, @AuthenticationPrincipal UserInfoVO user) {
		like.setBiNum(biNum);
		like.setUiNum(user.getUiNum());
		return likeInfoService.SelectLikeInfoByBiNum(like);
	}
}
