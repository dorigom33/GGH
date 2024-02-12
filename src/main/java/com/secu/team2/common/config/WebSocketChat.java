 package com.secu.team2.common.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secu.team2.common.vo.ChatVO;

import lombok.extern.slf4j.Slf4j;

@Component
@ServerEndpoint("/chat/{name}/{ciNum}")
@Slf4j
public class WebSocketChat {
	public static Map<Integer, List<Session>> ciNumSessionMap = Collections.synchronizedMap(new HashMap<>());
	private ObjectMapper om = new ObjectMapper();

	@OnOpen
	public void open(Session session, EndpointConfig ec, @PathParam("ciNum")Integer ciNum) throws IOException {
		List<Session> sessionList = new ArrayList<>();
		if(ciNumSessionMap.containsKey(ciNum)) {
			ciNumSessionMap.get(ciNum).add(session);
		}else {
			sessionList.add(session);
			ciNumSessionMap.put(ciNum, sessionList);
		}
		log.info("open ciNumSessionMap=>{}", ciNumSessionMap);
	}

	private void sendMsg(Session session, ChatVO chat) throws IOException {
		String json = om.writeValueAsString(chat);
		sendMsg(session, json);
	}

	private void sendMsg(Session session, String msg) throws IOException {
		session.getBasicRemote().sendText(msg);
	}

	@OnMessage
	public void msg(String data, Session session, @PathParam("name")String name, @PathParam("ciNum")Integer ciNum) throws IOException {
		ChatVO chat = new ChatVO();
		if(ciNumSessionMap.get(ciNum).size()>=2) {
			chat.setMsg(data);
			chat.setName(name);
			for (Session element : ciNumSessionMap.get(ciNum)) {
				if(!(element.equals(session)))
					sendMsg(element, chat);
			}
		}
	}

	@OnClose
	public void close(Session session, @PathParam("ciNum")Integer ciNum) throws IOException {
		ciNumSessionMap.get(ciNum).remove(ciNumSessionMap.get(ciNum).indexOf(session));
		if(ciNumSessionMap.get(ciNum).size() == 0) {
			ciNumSessionMap.remove(ciNum);
		}
		log.info("close ciNumSessionMap=>{}", ciNumSessionMap);
	}
}