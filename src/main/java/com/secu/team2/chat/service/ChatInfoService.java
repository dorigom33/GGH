package com.secu.team2.chat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.secu.team2.chat.mapper.ChatInfoMapper;
import com.secu.team2.chat.mapper.CheckedTimeInfoMapper;
import com.secu.team2.chat.mapper.MessageInfoMapper;
import com.secu.team2.chat.vo.ChatInfoVO;
import com.secu.team2.chat.vo.ChatRoomInfoVO;
import com.secu.team2.chat.vo.CheckedTimeInfoVO;
import com.secu.team2.chat.vo.MessageInfoVO;
import com.secu.team2.common.util.DateUtil;
import com.secu.team2.react.mapper.ChatMessageInfoMapper;
import com.secu.team2.react.vo.MessageVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatInfoService {
	
    private final ChatInfoMapper chatInfoMapper;
    private final MessageInfoMapper messageInfoMapper;
    private final ChatMessageInfoMapper chatMessageMapper;
    private final CheckedTimeInfoMapper checkedTimeInfoMapper;

    //채팅방 생성
    public int createChatRoom(ChatInfoVO chat) {
    	ChatInfoVO chatData = chatInfoMapper.selectChatInfo(chat);
    	if(chatData == null && chat.getBuyerUiNum()!=chat.getSellerUiNum()) {
    		if(chatInfoMapper.insertChatInfo(chat) == 1) {
    			CheckedTimeInfoVO checkedTime = new CheckedTimeInfoVO();
    			checkedTime.setCiNum(chat.getCiNum());
    			checkedTime.setUiNum(chat.getBuyerUiNum());
    			checkedTimeInfoMapper.insertCheckedTimeInfo(checkedTime);
    			checkedTime.setUiNum(chat.getSellerUiNum());
    			return checkedTimeInfoMapper.insertCheckedTimeInfo(checkedTime);
    		}
    	}else if(chatData != null) {
    		return 1;
    	}
    	return 0;
    }

    //채팅방 목록 조회(채팅방 목록 그리기용(더 많은 정보))
    public List<ChatRoomInfoVO> getChatInfoList(int uiNum){
    	List<ChatRoomInfoVO> chatRoomList = new ArrayList<>();
    	chatRoomList = chatInfoMapper.selectChatRoomList(uiNum);
    	for (ChatRoomInfoVO chatRoom : chatRoomList) {
//    		chatRoom.setCheckTime(checkedTimeInfoMapper.selectChatRoomList(chatRoom));
//    		chatRoom.setOpUiRank(rankInfoMapper.selectAverageRankInfo(chatRoom.getOpUiNum()));
    	}
    	return chatRoomList;
    }

    //채팅방 확인한 시간 갱신
    public int updateCheckedTimeInfo(CheckedTimeInfoVO checkTime) {
    	CheckedTimeInfoVO checkTimeInfo = new CheckedTimeInfoVO();
    	checkTimeInfo = checkedTimeInfoMapper.selectCheckedTimeInfo(checkTime);
    	if(checkTimeInfo == null) {
    		checkedTimeInfoMapper.insertCheckedTimeInfo(checkTime);
    	}
    	return checkedTimeInfoMapper.updateCheckedTimeInfo(checkTime);
    }
    
    //채팅 내용들 조회
    public List<MessageInfoVO> getMessageList(int ciNum) {
    	return messageInfoMapper.getMessageListByCiNum(ciNum);
    }

    //채팅 내용 저장
    public void saveMessage(MessageInfoVO message) {
    	messageInfoMapper.insertMessageInfo(message);
    }

    //채팅방 정보 조회
    public ChatInfoVO selectChatInfoByCiNum(int ciNum) {
        return chatInfoMapper.selectChatInfoByCiNum(ciNum);
    }

    //소속된 모든 채팅방에서 새로운 메세지 전체 갯수 조회
    public int countNewMessageChatRoom(int uiNum) {
    	int count = 0;
    	List<ChatInfoVO> chatList = chatInfoMapper.getChatInfoListByUiNum(uiNum);
    	for(ChatInfoVO chat : chatList) {
    		CheckedTimeInfoVO checkTime = new CheckedTimeInfoVO();
    		checkTime.setCiNum(chat.getCiNum());
    		checkTime.setUiNum(uiNum);
    		if (checkedTimeInfoMapper.countNewMessage(checkTime) > 0) {
    			count += checkedTimeInfoMapper.countNewMessage(checkTime);
    		}
    	}
    	return count;
    }
    
    // 해당 게시물의 채팅방 개수
    public int getChatInfoCntByBiNum(int biNum) {
    	return chatInfoMapper.selectChatInfoCntByBiNum(biNum);
    }
}
