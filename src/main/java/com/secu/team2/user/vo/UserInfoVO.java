package com.secu.team2.user.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import com.secu.team2.rank.vo.RankInfoVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserInfoVO implements UserDetails{
	private static final long serialVersionUID = 1L;	//직렬화 버전번호
	private int uiNum;				//사용자 번호
	private String uiId;			//아이디
	private String uiPwd;			//비밀번호
	private String uiName;			//이름
	private String uiEmail;			//이메일
	private String uiMobile;		//전화번호
	private String uiZonecode;		//우편번호
	private String uiRoadaddr;		//도로명주소
	private String uiDetailAddr;	//상세주소
	private String uiRole;			//사용자 역할(관리자, 일반사용자)
	private String credat;			//생성일자
	private String lmodat;			//최종수정일자
	private String active;			//사용자의 활성화/비활성화 여부
	private String uiDesc;			//자기소개
	private String uiImgName;		// 프사 이름
	private String uiImgPath;		// 프사 경로
	private MultipartFile file;
	private String token;
	private String sessionId;
	private String loginDate;
	private boolean login;
	
	//rankInfo
	private float riRank;
	private List<RankInfoVO> riComment;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {	//사용자 권한 설정 메서드
		Collection<GrantedAuthority> roles = new ArrayList<>();
		String[] uiRoleArr = uiRole.split(",");
		for(String uiRoles : uiRoleArr) {
			roles.add(new SimpleGrantedAuthority(uiRoles));
		}
		return roles;
	}
	//사용자 아이디와 비밀번호 반환 메서드
	@Override
	public String getPassword() {
		return uiPwd;
	}
	@Override
	public String getUsername() {
		return uiId;
	}
	//기타 계정 관리 메서드(미사용)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}

}
