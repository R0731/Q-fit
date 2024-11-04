package com.qfit.mvc.model.dao;

import java.util.List;

import com.qfit.mvc.model.dto.Exercise;

public interface ExerciseDao {
	// 모든 exercise 조회
	List<Exercise> selectAll();
	
	// exerciseId로 exercise를 조회(task와 연동)
	Exercise selectExerciseByExId(int exerciseId);
	
	// 운동부위별로 exercise 조회
	List<Exercise> selectExerciseByParts(String parts);
}
