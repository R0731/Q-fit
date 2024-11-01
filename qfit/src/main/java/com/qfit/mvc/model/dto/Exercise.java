package com.qfit.mvc.model.dto;
// DB의 운동 내용을 조회하기 위한 Exercise dto
public class Exercise {
	int exerciseId;
	String exerciseType;
	String exerciseParts;
	String exerciseName;
	
	// 기본 생성자 - 생성을 안해도 빈 등록을 위해 생성자, 접근자, 설정자 만드는 게 좋다.
	public Exercise() {
	}
	
	//모두 넣은 생성자
	public Exercise(int exerciseId, String exerciseType, String exerciseParts, String exerciseName) {
		this.exerciseId = exerciseId;
		this.exerciseType = exerciseType;
		this.exerciseParts = exerciseParts;
		this.exerciseName = exerciseName;
	}

	// 이하 접근자, 설정자
	public int getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}

	public String getExerciseType() {
		return exerciseType;
	}

	public void setExerciseType(String exerciseType) {
		this.exerciseType = exerciseType;
	}

	public String getExerciseParts() {
		return exerciseParts;
	}

	public void setExerciseParts(String exerciseParts) {
		this.exerciseParts = exerciseParts;
	}

	public String getExerciseName() {
		return exerciseName;
	}

	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}

	// to String
	@Override
	public String toString() {
		return "Exercise [exerciseId=" + exerciseId + ", exerciseType=" + exerciseType + ", exerciseParts="
				+ exerciseParts + ", exerciseName=" + exerciseName + "]";
	}	
	
}
