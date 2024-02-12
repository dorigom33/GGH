package com.secu.team2.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secu.team2.common.firebase.FirebaseService;
import com.secu.team2.rank.mapper.RankInfoMapper;
import com.secu.team2.user.mapper.UserInfoMapper;
import com.secu.team2.user.vo.MinUserInfoVO;
import com.secu.team2.user.vo.UserInfoVO;
import com.secu.team2.user.vo.UserProfileVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService {

	private final UserInfoMapper userInfoMapper; // 생성자 주입
	private final PasswordEncoder passwordEncoder; // 생성자 주입
	private final FirebaseService firebaseService;
	private final RankInfoMapper rankInfoMapper;

	@Value("${upload.file-path}")
	private String uploadFilePath;
	
	@Override
	public UserInfoVO loadUserByUsername(String uiId) throws UsernameNotFoundException { // 사용자 아이디로 사용자 정보 조회
		UserInfoVO user = userInfoMapper.selectUserInfoByUiId(uiId);

		if (user == null || "0".equals(user.getActive())) {
			throw new UsernameNotFoundException("User not found or inactive");
		}

		return user;
	}

	public UserInfoVO selectUserInfo(int uiNum) { // 사용자 번호로 사용자 정보 조회
		UserInfoVO user = new UserInfoVO();
		user = userInfoMapper.selectUserInfo(uiNum);
		user.setRiComment(rankInfoMapper.selectCountRivewInfo(uiNum));
		user.setRiRank(rankInfoMapper.selectAverageRankInfo(uiNum));
		return user;
	}

	public UserInfoVO selectUiNumByUiName(String uiName) {	//사용자 이름으로 사용자 번호 조회
		return userInfoMapper.selectUiNumByUiName(uiName);
	}

	public UserProfileVO getUserProfile(int uiNum) {	//사용자 번호로 유저 프로필 정보 조회
		UserProfileVO profile = new UserProfileVO();
		profile = userInfoMapper.getUserProfile(uiNum);
		profile.setRiComment(rankInfoMapper.selectCountRivewInfo(uiNum));
		profile.setRiRank(rankInfoMapper.selectAverageRankInfo(uiNum));
		if(profile.getUiRoadaddr() != null) {
			int index = profile.getUiRoadaddr().indexOf(" ");
			index = profile.getUiRoadaddr().indexOf(" ", index+1);
			profile.setUiRoadaddr(profile.getUiRoadaddr().substring(0, index-1));
		}
		return profile;
	}

	public UserInfoVO login(UserInfoVO user) { // 로그인 메서드
		UserInfoVO loginUser = userInfoMapper.selectUserInfoByIdAndPwd(user);

		if (loginUser != null && "1".equals(loginUser.getActive())) { // 사용자 정보가 존재하거나 사용자가 활성화 상태일 경우
			// 정상적인 로그인
			log.info("User {} logged in successfully.", loginUser.getUiId());
		} else {
			loginUser = null;
			log.info("Invalid or inactive account login attempt for user: {}", user.getUiId());
		}

		return loginUser;
	}

	public boolean isPasswordCorrect(String uiId, String inputPassword) {
	    UserInfoVO userInfo = userInfoMapper.selectUserInfoByUiId(uiId);

	    if (userInfo != null && "1".equals(userInfo.getActive())) {
	        try {
	            Map<String, String> passwordMap = new ObjectMapper().readValue(inputPassword, Map.class);

	            String extractedPassword = passwordMap.get("password");

	            log.info("inputPassword=>{}", extractedPassword);
	            log.info("getUiPwd=>{}", userInfo.getUiPwd());
	            log.info("matches=>{}", passwordEncoder.matches(extractedPassword, userInfo.getUiPwd()));

	            return passwordEncoder.matches(extractedPassword, userInfo.getUiPwd());
	        } catch (Exception e) {
	            // 예외 처리
	            log.error("Error parsing inputPassword", e);
	            return false;
	        }
	    }

	    return false;
	}


	public MinUserInfoVO selectMinUserInfoByUiNum(int uiNum) {
		return userInfoMapper.selectMinUserInfoByUiNum(uiNum);
	}

	public List<UserInfoVO> getUserInfos(UserInfoVO user) { // 전체 사용자 리스트 조회
		return userInfoMapper.selectUserInfos(user);
	}

	public int insertUserInfo(UserInfoVO user) { // 회원가입
		user.setUiPwd(passwordEncoder.encode(user.getUiPwd()));
		log.info("uiPwd=>{}", user.getUiPwd());
		if(user.getFile() != null) {
			String originName = user.getFile().getOriginalFilename();
			try {
                String filePath = firebaseService.uploadFile(user.getFile(), originName);
                user.setUiImgName(originName);
                user.setUiImgPath(filePath);
            } catch (Exception e) {
                log.error("File upload error=>{}", e);
                user.setUiImgName(originName);
                user.setUiImgPath("/upload-failed/" + originName);
            }
		}else {
            user.setUiImgName("No File");
            user.setUiImgPath("No Path");
        }
		return userInfoMapper.insertUserInfo(user);
	}

	public int updateUserInfo(UserInfoVO user) { // 회원정보 수정
		MultipartFile file = user.getFile();
		if (file != null) {
            String originName = file.getOriginalFilename();
            try {
                String filePath = firebaseService.uploadFile(file, originName);
                user.setUiImgName(originName);
                user.setUiImgPath(filePath);
            } catch (Exception e) {
                log.error("File upload error=>{}", e);
                user.setUiImgName(originName);
                user.setUiImgPath("/upload-failed/" + originName);
            }
        }
		user.setUiPwd(passwordEncoder.encode(user.getUiPwd()));
		return userInfoMapper.updateUserInfo(user);
	}

	public int updateUserActive(int uiNum) { // 사용자 비활성화
		return userInfoMapper.updateUserActive(uiNum);
	}

	public int deleteUserInfo(int uiNum) { // 사용자 삭제
		return userInfoMapper.deleteUserInfo(uiNum);
	}
	
	// 아이디 중복 여부 체크
	public int selectUserInfoCntByUiId(String uiId) {
		return userInfoMapper.selectUserInfoCntByUiId(uiId);
	}
	
	public List<UserInfoVO> selectUserInfosForChat(int uiNum){
		return userInfoMapper.selectUserInfosForChat(uiNum);
	}
	
}