package com.secu.team2.chat.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.secu.team2.chat.service.ChatInfoService;
import com.secu.team2.chat.vo.ChatInfoVO;
import com.secu.team2.chat.vo.ChatRoomInfoVO;
import com.secu.team2.chat.vo.CheckedTimeInfoVO;
import com.secu.team2.chat.vo.MessageInfoVO;
import com.secu.team2.user.vo.UserInfoVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ChatInfoController {

    private final ChatInfoService chatInfoService;
	private ObjectMapper om = new ObjectMapper();

    @GetMapping("/get-chat-room-list")//채팅방 목록 조회
    public List<ChatRoomInfoVO> getChatRoomInfoList(@AuthenticationPrincipal UserInfoVO user) throws JsonProcessingException{
    	return chatInfoService.getChatInfoList(user.getUiNum());
    }

    @PostMapping("/create-chat-room")//채팅방 생성(보드 뷰에서 생성)
    public int createChatRoom(@AuthenticationPrincipal UserInfoVO user, @RequestBody ChatInfoVO chat) throws JsonProcessingException {
    	chat.setBuyerUiNum(user.getUiNum());
    	return chatInfoService.createChatRoom(chat);
    }

    @PostMapping("/save-message")//채팅 내용 저장
    public void saveMessage(@RequestBody MessageInfoVO message) {
    	chatInfoService.saveMessage(message);
    }

    @GetMapping("/get-message-list/{ciNum}")//채팅 내용들 조회
    public List<MessageInfoVO> getMessageList(@PathVariable int ciNum) {
    	return chatInfoService.getMessageList(ciNum);
    }

    @PostMapping("/update-check-time")//채팅을 확인한 시간 갱신
    public void updateCheckedTimeInfo(@RequestBody CheckedTimeInfoVO checkTime) {
    	chatInfoService.updateCheckedTimeInfo(checkTime);
    }

    @GetMapping("/count-new-message-chat-room")//새로운 메세지 갯수 조회
    public int countNewMessageChatRoom(@AuthenticationPrincipal UserInfoVO user) {
    	if(user != null) {
	    	return chatInfoService.countNewMessageChatRoom(user.getUiNum());
    	}
    	return 0;
    }
}