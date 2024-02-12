package com.secu.team2.react.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.secu.team2.common.util.DateUtil;
import com.secu.team2.react.mapper.ChatMessageInfoMapper;
import com.secu.team2.react.vo.MessageVO;

@Service
public class ChatService {
	@Autowired
	private ChatMessageInfoMapper chatMessageMapper;
    
    public int insertChatMessageInfo(MessageVO message) {
    	message.setCmiSentTime(DateUtil.getToDate());
    	return chatMessageMapper.insertChatMessageInfo(message);
    }
    
    public PageInfo<MessageVO> selectChatMessageInfos(MessageVO message){
    	PageHelper.startPage(1,100);
    	return PageInfo.of(chatMessageMapper.selectChatMessageInfoById(message));
    }
    
    public boolean updateChatMessageInfoReceivedTime(MessageVO message) {
    	return chatMessageMapper.updateChatMessageInfoReceivedTime(message) == 1;
    }
}
