package com.secu.team2.viewed.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.secu.team2.viewed.mapper.ViewedInfoMapper;
import com.secu.team2.viewed.vo.ViewedInfoVO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class ViewedInfoService {
	private final ViewedInfoMapper viewedInfoMapper;

	// 최근 방문 게시글 추가
	public int InsertViewedInfo(ViewedInfoVO viewed) {
		log.info("viewed=>{}", viewed);
		return viewedInfoMapper.InsertViewedInfo(viewed);
	}

	// 최근 방문 게시글 리스트
	public List<ViewedInfoVO> SelectViewedInfos(ViewedInfoVO viewed){
		log.info("viewed=>{}", viewed);
		return viewedInfoMapper.SelectViewedInfos(viewed);
	}
}
