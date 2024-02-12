package com.secu.team2.report.mapper;

import java.util.List;

import com.secu.team2.report.vo.ReportUserInfoVO;

public interface ReportInfoMapper {
	List<ReportUserInfoVO> getReportInfo();
	int insertReportInfo(ReportUserInfoVO report);
	int updateReportInfoStatus(ReportUserInfoVO report);
}
