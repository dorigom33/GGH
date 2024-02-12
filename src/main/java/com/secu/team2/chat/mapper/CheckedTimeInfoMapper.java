package com.secu.team2.chat.mapper;

import com.secu.team2.chat.vo.ChatRoomInfoVO;
import com.secu.team2.chat.vo.CheckedTimeInfoVO;
import com.secu.team2.chat.vo.LmotimCretimMapVO;

public interface CheckedTimeInfoMapper {
	CheckedTimeInfoVO selectCheckedTimeInfo(CheckedTimeInfoVO checkTime);
	LmotimCretimMapVO selectChatRoomList(ChatRoomInfoVO chatRoom);
	int insertCheckedTimeInfo(CheckedTimeInfoVO checkTime);
	int updateCheckedTimeInfo(CheckedTimeInfoVO checkTime);
	int countNewMessage(CheckedTimeInfoVO checkTime);
}
