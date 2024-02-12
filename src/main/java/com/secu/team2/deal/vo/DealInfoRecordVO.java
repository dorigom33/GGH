package com.secu.team2.deal.vo;

import java.util.List;

import com.secu.team2.file.vo.FileInfoVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DealInfoRecordVO {
	private int biNum;
	private int diNum;
	private int sellerUiNum;
	private int buyerUiNum;
	private String buyerDiStat;
	private String credat;
	private String cretim;
	private int oiPrice;
	private String riRank;
	private String riComment;
	private String biTitle;
	private List<FileInfoVO> files;
}
