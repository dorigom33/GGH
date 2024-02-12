package com.secu.team2.report.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReportUserInfoVO {
	private int ruiNum;
	private int complUiNum;
	private int targetUiNum;
	private String ruiDesc;
	private int ruiStat;
	private String credat;
	private String cretim;
}
