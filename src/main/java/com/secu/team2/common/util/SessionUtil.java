package com.secu.team2.common.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.secu.team2.user.vo.UserInfoVO;

public class SessionUtil {
	
	public static Object getSessionUser() {
		if(SecurityContextHolder.getContext().getAuthentication()==null) {
			return "Non-session";
		}
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	public static boolean isLogin() {
		if(getSessionUser() instanceof UserInfoVO) {
			return true;
		}
		return false;
	}
}
