package com.secu.team2.user.vo;

import java.util.List;

import com.secu.team2.rank.vo.RankInfoVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserProfileVO {
	//userInfo
	private int uiNum;
	private String uiName;
	private String credat;
	private String uiDesc;
	private String uiRoadaddr;
	private String uiImgName;		// 프사 이름
	private String uiImgPath;		// 프사 경로

	//rankInfo
	private float riRank;
	private List<RankInfoVO> riComment;
}
