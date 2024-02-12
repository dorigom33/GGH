package com.secu.team2.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.secu.team2.common.provider.JWTTokenProvider;
import com.secu.team2.common.util.SessionUtil;
import com.secu.team2.user.service.UserInfoService;
import com.secu.team2.user.vo.UserInfoVO;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

	private final JWTTokenProvider jwtProvider;
	private final UserInfoService userService;
	private String includeUri = "/auth";
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest req) throws ServletException{
		return "none".equals(req.getHeader("sec-fetch-site"))
				|| "same-origin".equals(req.getHeader("sec-fetch-site"));
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(token != null && !token.isEmpty()) {
			token = token.replace("Bearer ","");
			Map<String,Object> error = new HashMap<>();
			String msg = null;
			try {
				if(jwtProvider.validation(token)) {
					String uiId = jwtProvider.getId(token);
					UserInfoVO user = (UserInfoVO)userService.loadUserByUsername(uiId);
					user.setUiPwd(null);
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}catch(ExpiredJwtException e) {
				msg = "토큰 유효기간 만료";
			}catch(UnsupportedJwtException e) {
				msg = "지원하지 않는 토큰입니다.";
			}catch(SignatureException | MalformedJwtException e) {
				msg = "토큰 형식이 맞지 않습니다.";
			}
			if(msg != null) {
				error.put("msg",msg);
				errorJsonWrite(response, error, HttpStatus.FORBIDDEN);
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	private static void errorJsonWrite(HttpServletResponse res, Map<String,Object> error, HttpStatus status) {
		res.setContentType("application/json;charset=UTF-8");
		res.setStatus(status.value());
		try {
			PrintWriter out = res.getWriter();
			JSONObject json = new JSONObject(error);
			out.print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
