package com.secu.team2.common.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import com.secu.team2.user.service.UserInfoService;
import com.secu.team2.user.vo.UserInfoVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

	public static List<Integer> uiNums = Collections.synchronizedList(new ArrayList<>());
	public static List<String> sessionIds = Collections.synchronizedList(new ArrayList<>());
	public static List<UserInfoVO> users = Collections.synchronizedList(new ArrayList<>());
	public static Map<String, UserInfoVO> connectedMap = Collections.synchronizedMap(new HashMap<>());
	
	private final SimpMessagingTemplate smt;
	private final UserInfoService userService;
	
	@EventListener
	public void connnectionListener(SessionConnectedEvent evt) {
		
		StompHeaderAccessor sha = StompHeaderAccessor.wrap(evt.getMessage()); // 페이로드 인스턴스화
		GenericMessage<?> gm = (GenericMessage<?>) sha.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER); // 페이로드의 헤더 추출
		SimpMessageHeaderAccessor smha = SimpMessageHeaderAccessor.wrap(gm); // 추출한 헤더를 다시 인스턴스화
		int uiNum = Integer.parseInt(smha.getFirstNativeHeader("uiNum"));
		UserInfoVO connectedUser = userService.selectUserInfo(uiNum);
		String sessionId = sha.getSessionId();
		connectedUser.setLogin(true);
		connectedMap.put(sessionId, connectedUser);
		smt.convertAndSend("topic/enter-chat", connectedUser);
	}

	@EventListener
	public void disconnnectionListener(SessionDisconnectEvent evt) {
		StompHeaderAccessor sha = StompHeaderAccessor.wrap(evt.getMessage());
		String sessionId = sha.getSessionId();
		UserInfoVO disconnectedUser = connectedMap.remove(sessionId);
		smt.convertAndSend("/topic/enter-chat",disconnectedUser);
	}
	
	@EventListener
	public void subscribeListener(SessionSubscribeEvent evt) {
		String destination = (String) evt.getMessage().getHeaders().get("simpDestination");
		log.info(destination);
		if("/topic/enter-chat".equals(destination)) {
			smt.convertAndSend("/topic/enter-chat",connectedMap);
		}
		log.info("subscribe=>{}", evt);
	}
	
	@EventListener
	public void unsubscribeListener(SessionUnsubscribeEvent evt) {
		log.info("unsubscribeListener=>{}", evt);
		String destination = (String) evt.getMessage().getHeaders().get("simpDestination");
		if("/topic/enter-chat".equals(destination)) {
			smt.convertAndSend("/topic/enter-chat",users);
		}
	}
	
}
