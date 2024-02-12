package com.secu.team2.common.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MsgVO {
    private String msg;
    private String url;
    private boolean success;
}