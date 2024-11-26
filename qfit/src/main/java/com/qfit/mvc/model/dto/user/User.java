package com.qfit.mvc.model.dto.user;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qfit.mvc.model.dao.user.TrainerDao;
import com.qfit.mvc.model.dao.user.UserDao;

@Alias("User")
public class User {
	protected int id; // user 고유 ID
	private String userId; // user가 입력한 ID
	private String userPassword; // 패스워드
	private String userName; // 이름
	private String phoneNumber; // 휴대폰 번호
	private String email; // 이메일
	private String gender; // 성별
	private String userImg; // 프로필 이미지 주소
	private String createdAt; // 계정 생성 시각
	protected int userType; // 유저 타입
	private boolean isAgreed; // 개인정보 선택 동의 여부
	
	// 인식이 제대로 되지 않아 직접 매핑
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("birthDate")
	private String birthDate;
    
    // 생성자
	public User() {
		// TODO Auto-generated constructor stub
	}

	//회원 가입을 할 때 사용될 생성자
	public User(int id, String userId, String userPassword, String userName, String phoneNumber, String email,
			String gender, String birthdate, String createdAt, int userType) {
		this.id = id;
		this.userId = userId;
		this.userPassword = userPassword;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.gender = gender;
		this.birthDate = birthdate;
		this.createdAt = createdAt;
		this.userType = userType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthdate() {
		return birthDate;
	}

	public void setBirthdate(String birthdate) {
		this.birthDate = birthdate;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public boolean isAgreed() {
		return isAgreed;
	}

	public void setAgreed(boolean isAgreed) {
		this.isAgreed = isAgreed;
	}

	
}
