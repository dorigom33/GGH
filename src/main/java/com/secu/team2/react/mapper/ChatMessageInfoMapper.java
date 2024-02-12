package com.secu.team2.react.mapper;

import java.util.List;

import com.secu.team2.react.vo.MessageVO;

public interface ChatMessageInfoMapper {
	List<MessageVO> selectChatMessageInfoById(MessageVO message);
	int insertChatMessageInfo(MessageVO message);
	int updateChatMessageInfoReceivedTime(MessageVO message);
}
