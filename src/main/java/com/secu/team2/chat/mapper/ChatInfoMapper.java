package com.secu.team2.chat.mapper;

import java.util.List;

import com.secu.team2.chat.vo.ChatInfoVO;
import com.secu.team2.chat.vo.ChatRoomInfoVO;

public interface ChatInfoMapper {
	List<ChatInfoVO> getChatInfoListByUiNum(int uiNum);
	ChatInfoVO getChatInfoByCiId(int ciNum);
	ChatInfoVO selectChatInfo(ChatInfoVO chatInfo);
	ChatInfoVO selectChatInfoByCiNum(int ciNum);
	List<ChatRoomInfoVO> selectChatRoomList(int uiNum);
	int insertChatInfo(ChatInfoVO chatInfo);
	int selectChatInfoCntByBiNum(int biNum); // 해당 게시물의 채팅방 개수
}
