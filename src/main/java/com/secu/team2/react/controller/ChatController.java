package com.secu.team2.react.controller;

import java.util.List;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.secu.team2.common.util.DateUtil;
import com.secu.team2.react.service.ChatService;
import com.secu.team2.react.vo.MessageVO;
import com.secu.team2.user.service.UserInfoService;
import com.secu.team2.user.vo.UserInfoVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class ChatController {

	private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate; 
    private final UserInfoService userService;

    @MessageMapping("/chat/{uiNum}")
    public void chat(@DestinationVariable Integer uiNum, MessageVO message, @AuthenticationPrincipal UserInfoVO user) {
    	log.info("msg=>{}", message);
    	if(message.getCmiMessage() != null) {
    		chatService.insertChatMessageInfo(message);
    	}
        simpMessagingTemplate.convertAndSend("/topic/chat/" + uiNum, message);
    }
    
    @GetMapping("/chat-list")
    public PageInfo<MessageVO> message(@ModelAttribute MessageVO message){
    	log.info("message=>{}", message);
    	return chatService.selectChatMessageInfos(message);
    }
    
    @GetMapping("/chat-user-infos/{uiNum}")
    public List<UserInfoVO> selectUserInfosForChat(@PathVariable("uiNum") int uiNum){
    	return userService.selectUserInfosForChat(uiNum);
    }
    
    @PutMapping("/chat-list")
    public boolean chatList(@RequestBody MessageVO message) {
    	message.setCmiReceivedTime(DateUtil.getToDate());
    	return chatService.updateChatMessageInfoReceivedTime(message);
    }
}
