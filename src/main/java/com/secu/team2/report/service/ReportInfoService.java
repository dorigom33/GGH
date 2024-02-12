package com.secu.team2.report.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.secu.team2.report.mapper.ReportInfoMapper;
import com.secu.team2.report.vo.ReportUserInfoVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportInfoService {
	private final ReportInfoMapper reportInfoMapper;
	
	public int insertReportInfo(ReportUserInfoVO report) {
		return reportInfoMapper.insertReportInfo(report);
	}
	
	public List<ReportUserInfoVO> getReportInfo() {
		return reportInfoMapper.getReportInfo();
	}

	public int updateReportInfoStatus(ReportUserInfoVO report) {
		return reportInfoMapper.updateReportInfoStatus(report);
	}
}
