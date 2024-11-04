package com.qfit.mvc.model.service;

import java.util.List;

import com.qfit.mvc.model.dto.Exercise;

public interface ExerciseService {
	
	// 전체 운동 조회
	public List<Exercise> getExerciseList();
	// id로 개별 운동 조회
	public Exercise getExerciseListById(int exerciseId);
	// 부위별 운동 조회
	public List<Exercise> getExerciseListByParts(String exerciseParts);

}
