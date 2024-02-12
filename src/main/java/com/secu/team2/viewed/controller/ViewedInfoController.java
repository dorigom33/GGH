package com.secu.team2.viewed.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.secu.team2.user.vo.UserInfoVO;
import com.secu.team2.viewed.service.ViewedInfoService;
import com.secu.team2.viewed.vo.ViewedInfoVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ViewedInfoController {
	private final ViewedInfoService viewedInfoService;

	public ViewedInfoController(@Autowired ViewedInfoService viewedInfoService) {
		this.viewedInfoService = viewedInfoService;
	}

	// 최근 방문 게시물 추가
	@PostMapping("/viewed-infos/{biNum}")
	public int addViewedInfo(ViewedInfoVO viewed, @PathVariable int biNum, @AuthenticationPrincipal UserInfoVO user) {
		viewed.setBiNum(biNum);
		viewed.setUiNum(user.getUiNum());
		log.info("viewed=>{}", viewed);
		return viewedInfoService.InsertViewedInfo(viewed);
	}

	// 최근 방문 게시물 리스트
	@GetMapping("/viewed-infos")
	public List<ViewedInfoVO> getViewedInfos(ViewedInfoVO viewed, @AuthenticationPrincipal UserInfoVO user){
		viewed.setUiNum(user.getUiNum());
		log.info("viewed=>{}", viewed);
		return viewedInfoService.SelectViewedInfos(viewed);
	}
}
