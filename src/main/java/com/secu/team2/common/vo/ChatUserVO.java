package com.secu.team2.common.vo;

import java.io.Serializable;

import javax.websocket.Session;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatUserVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Session session;
	private String name;
}
