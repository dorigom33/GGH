package com.secu.team2.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secu.team2.common.listener.WebSocketEventListener;
import com.secu.team2.common.provider.JWTTokenProvider;
import com.secu.team2.common.util.DateUtil;
import com.secu.team2.user.vo.UserInfoVO;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	private final JWTTokenProvider jwt;
	private final ObjectMapper om;
	
	public CustomAuthenticationFilter(AuthenticationManager manager, JWTTokenProvider jwt, ObjectMapper om) {
		super(manager);
		this.jwt = jwt;
		this.om = om;
	}
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		try {
			UserInfoVO user = om.readValue(request.getInputStream(), UserInfoVO.class);
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUiId(), user.getUiPwd());
			setDetails(request, authRequest);
			return this.getAuthenticationManager().authenticate(authRequest);
		}catch(Exception e) {
			throw new AuthenticationServiceException("로그인 오류");
		}
	}
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		UserInfoVO user = (UserInfoVO)authResult.getPrincipal();
				String token = jwt.generateToken(authResult);
				user.setToken(token);
				user.setUiPwd(null);
		ResponseCookie resCookie = ResponseCookie.from(HttpHeaders.AUTHORIZATION, token)
				.httpOnly(true)
				.sameSite("None")
				.secure(true)
				.path("/")
				.maxAge(jwt.getTokenExpire())
				.build();
		response.addHeader(HttpHeaders.SET_COOKIE, resCookie.toString());
		jsonWrite(response, om.writeValueAsString(user));
	}

	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		Map<String,Object> body = new HashMap<>(); 
		body.put("msg", "로그인 실패");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		jsonWrite(response, body);
	}

	private static void jsonWrite(HttpServletResponse res, String json) throws IOException {
		res.setContentType("application/json;charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

	private static void jsonWrite(HttpServletResponse res, Map<String,Object> body) throws IOException {
		res.setContentType("application/json;charset=UTF-8");
		PrintWriter out = res.getWriter();
		JSONObject json = new JSONObject(body);
		out.print(json);
		out.flush();
		out.close();
	}
}