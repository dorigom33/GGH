package com.secu.team2.chat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatRoomInfoVO {
	private int ciNum;
	private int biNum;
	private int uiNum;
	private int biPrice;
	private int opUiNum;
	private int buyerUiNum;
	private int sellerUiNum;
	private float opUiRank;
	private String biTitle;
	private String biStat;
	private String uiName;
	private String opUiName;
	private LmotimCretimMapVO checkTime;
	private String fiPath;
}
