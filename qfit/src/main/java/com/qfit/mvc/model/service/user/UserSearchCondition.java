package com.qfit.mvc.model.service.user;

// 추후 삭제 예정
public class UserSearchCondition {
	
	private String key = "이름"; // userId, 이름, 체육관으로 선택
	private String word;
	
	public UserSearchCondition() {
	}

	public UserSearchCondition(String key, String word) {
		this.key = key;
		this.word = word;
	}
	
}