package com.secu.team2.user.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.secu.team2.common.vo.MsgVO;
import com.secu.team2.rank.service.RankInfoService;
import com.secu.team2.user.service.UserInfoService;
import com.secu.team2.user.vo.MinUserInfoVO;
import com.secu.team2.user.vo.UserInfoVO;
import com.secu.team2.user.vo.UserProfileVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserInfoController {
	private final UserInfoService userInfoService;

	@GetMapping("/select-user-info")
	public UserInfoVO selectUserInfo(@AuthenticationPrincipal UserInfoVO userDetails) {		//회원번호로 로그인중인 사용자 정보 조회
		int uiNum = userDetails.getUiNum();
		UserInfoVO userInfo = userInfoService.selectUserInfo(uiNum);
		return userInfo;
	}

	@GetMapping("/selected-user-info/{uiNum}")
	public UserInfoVO selectUserInfo(@PathVariable int uiNum) {		//회원번호로 로그인중인 사용자 정보 조회
		return userInfoService.selectUserInfo(uiNum);
	}

	@GetMapping("/select-user-infos")
	public List<UserInfoVO> getUserInfos(UserInfoVO user) {		//전체 사용자 리스트 조회
		return userInfoService.getUserInfos(user);
	}

    @GetMapping("/selectMinUserInfo")
    public MinUserInfoVO getUserName(@AuthenticationPrincipal UserInfoVO user) throws JsonProcessingException {
    	MinUserInfoVO minUserInfoVO = new MinUserInfoVO();
    	minUserInfoVO.setUiName(user.getUiName());
    	minUserInfoVO.setUiNum(user.getUiNum());
    	return minUserInfoVO;
    }
    
    // 회원가입
	@PostMapping("/join")
	public UserInfoVO join(UserInfoVO user) {
		user.setUiMobile(user.getUiMobile().replace("-", "")); // 하이픈 없애기
		if (userInfoService.insertUserInfo(user) == 1) {
			user = userInfoService.loadUserByUsername(user.getUiId());
		}
		return user;
	}
	
	// 아이디 중복 여부 체크
	@GetMapping("/join/{uiId}")
	public MsgVO checkUserId(@PathVariable String uiId) {
		MsgVO msg = new MsgVO();
		msg.setMsg("사용할 수 없는 아이디입니다.");
		msg.setSuccess(false);
		if(userInfoService.selectUserInfoCntByUiId(uiId) == 0) {
			msg.setMsg("사용할 수 있는 아이디입니다.");
			msg.setSuccess(true);
		}
		log.info("msg=>{}", msg);
		return msg;
	}

	@PostMapping("/update-user-info")
	public MsgVO updateUserInfo(UserInfoVO user, @AuthenticationPrincipal UserInfoVO userDetails) {	//사용자 정보 수정(로그인 중인 사용자의 정보 기반)
		MsgVO msg = new MsgVO();
		int uiNum = userDetails.getUiNum();	//사용자 번호를 기반으로 회원정보 수정
		user.setUiNum(uiNum);
		log.info("uiNum=>{}", uiNum);
		user.setUiMobile(user.getUiMobile().replace("-", "")); // 하이픈 없애기
		int result = userInfoService.updateUserInfo(user);	//회원정보 수정 결과
		if (result > 0) {									//수정 성공시
			msg.setMsg("사용자 정보가 수정되었습니다. 다시 로그인 해주세요");	//성공 메시지 출력
			msg.setSuccess(true);
		} else {											//수정 실패시
			msg.setMsg("사용자 정보 수정에 실패했습니다.");	//실패 메시지 출력
		}
		return msg;											//메시지 반환
	}

	@PutMapping("/update-user-active")
	public MsgVO updateUserActive(@AuthenticationPrincipal UserInfoVO userDetails) {		//사용자 활성상태 변경(마이페이지에서 사용)
		MsgVO msg = new MsgVO();
		if (userDetails != null) {
			int uiNum = userDetails.getUiNum();
			int result = userInfoService.updateUserActive(uiNum);
			if (result > 0) {
				msg.setMsg("탈퇴되었습니다.");
				msg.setSuccess(true);
			} else {
				msg.setMsg("탈퇴에 실패했습니다.");
			}
		} else {
			msg.setMsg("업데이트 권한이 없습니다.");
		}
		return msg;
	}

	@PutMapping("/update-user-active/{uiNum}")
	public MsgVO updateUserActive(@AuthenticationPrincipal UserInfoVO userDetails, @PathVariable int uiNum) {		//사용자 활성상태 변경(관리자 페이지에서 사용)
		MsgVO msg = new MsgVO();
		if (userDetails != null) {
			int result = userInfoService.updateUserActive(uiNum);
			if (result > 0) {
				msg.setMsg("탈퇴되었습니다.");
				msg.setSuccess(true);
			} else {
				msg.setMsg("탈퇴에 실패했습니다.");
			}
		} else {
			msg.setMsg("업데이트 권한이 없습니다.");
		}
		return msg;
	}

	@PostMapping("/check-password")
    public MsgVO checkPassword(@AuthenticationPrincipal UserInfoVO userDetails, @RequestBody String inputPassword) {
        MsgVO msg = new MsgVO();

        if (userDetails != null) {
            String uiId = userDetails.getUiId();
            boolean isPasswordCorrect = userInfoService.isPasswordCorrect(uiId, inputPassword);
            if (isPasswordCorrect) {
                msg.setMsg("비밀번호가 일치합니다.");
                msg.setSuccess(true);
            } else {
                msg.setMsg("비밀번호가 일치하지 않습니다.");
            }
        } else {
            msg.setMsg("로그인이 필요합니다.");
        }

        return msg;
    }

	@PostMapping("/deleteUserInfo")
	public MsgVO deleteUserInfo(@AuthenticationPrincipal UserInfoVO userDetails) {		//사용자 삭제
		MsgVO msg = new MsgVO();
		if (userDetails != null) {			//사용자 정보가 있는지 확인
			int uiNum = userDetails.getUiNum();		//사용자 번호를 기반으로 삭제
			int result = userInfoService.deleteUserInfo(uiNum);		//회원정보 삭제 결과
			if (result > 0) {										//삭제 성공시
				msg.setMsg("사용자 정보가 삭제되었습니다.");		//성공 메시지 출력
				msg.setSuccess(true);
			} else {												//삭제 실패시
				msg.setMsg("사용자 정보 삭제에 실패했습니다.");		//실패 메시지 출력
			}
		} else {
			msg.setMsg("삭제 권한이 없습니다.");					//기타 예외처리(사용자 삭제 권한 여부)
		}
		return msg;													//결과 반환
	}

	@GetMapping("/get-user-profile/{uiNum}")
	public UserProfileVO getUserProfile(@PathVariable int uiNum) {
		return userInfoService.getUserProfile(uiNum);
	}
	
	
}
