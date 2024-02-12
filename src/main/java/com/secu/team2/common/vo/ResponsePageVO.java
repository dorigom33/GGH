package com.secu.team2.common.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponsePageVO<T> {
	private List<T> list;
	private int totalCnt;
}
