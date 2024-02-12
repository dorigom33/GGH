package com.secu.team2.report.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.secu.team2.report.service.ReportInfoService;
import com.secu.team2.report.vo.ReportUserInfoVO;
import com.secu.team2.user.vo.UserInfoVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReportInfoController {
	private final ReportInfoService reportInfoService;
	
	@PostMapping("/insert-report-info")
	public int insertReportInfo(@AuthenticationPrincipal UserInfoVO user ,@RequestBody ReportUserInfoVO report) {
		report.setComplUiNum(user.getUiNum());
		return reportInfoService.insertReportInfo(report);
	}

	@GetMapping("/get-report-info")
	public List<ReportUserInfoVO> getReportInfo() {
		return reportInfoService.getReportInfo();
	}

	@PostMapping("/update-report-info-status")
	public int updateReportInfoStatus(@RequestBody ReportUserInfoVO report) {
		return reportInfoService.updateReportInfoStatus(report);
	}
}
