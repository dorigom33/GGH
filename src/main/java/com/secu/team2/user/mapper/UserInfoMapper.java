package com.secu.team2.user.mapper;

import java.util.List;

import com.secu.team2.user.vo.MinUserInfoVO;
import com.secu.team2.user.vo.UserInfoVO;
import com.secu.team2.user.vo.UserProfileVO;

public interface UserInfoMapper {
	UserInfoVO selectUserInfoByUiId(String uiId); // 사용자 아이디로 사용자 정보 조회
	UserInfoVO selectUserInfo(int uiNum); // 사용자 번호로 사용자 정보 조회
	UserInfoVO selectUiNumByUiName(String uiName); // 사용자 이름으로 사용자 번호 조회
	UserInfoVO selectUserInfoByIdAndPwd(UserInfoVO user); // 아이디와 비밀번호로 사용자 정보 조회
	MinUserInfoVO selectMinUserInfoByUiNum(int uiNum); // 사용자 번호로 유저 이름만 조회
	UserProfileVO getUserProfile(int uiNum); // 사용자 번호로 유저 프로필 정보 조회
	List<UserInfoVO> selectUserInfos(UserInfoVO user); // 모든 사용자 정보를 리스트로 조회(관리자 계정에서 사용)
	int insertUserInfo(UserInfoVO user); // 사용자 등록(회원가입)
	int updateUserInfo(UserInfoVO user); // 사용자 정보 수정(개인정보 변경)
	int updateUserActive(int uiNum); // 사용자 비활성화
	int deleteUserInfo(int uiNum); // 사용자 삭제
	int selectUserInfoCntByUiId(String uiId); // 아이디 중복 여부 체크(회원가입)
	List<UserInfoVO> selectUserInfosForChat(int uiNum);
}