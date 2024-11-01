package com.qfit.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qfit.mvc.model.dao.ExerciseDao;
import com.qfit.mvc.model.dto.Exercise;

@Service
public class ExerciseServiceImpl implements ExerciseService {
	
	// Dao 의존성 주입
	private final ExerciseDao exerciseDao;
	
	public ExerciseServiceImpl(ExerciseDao exerciseDao) {
		this.exerciseDao = exerciseDao;
	}

	// 모든 운동리스트 조회
	@Override
	public List<Exercise> getExerciseList() {
		System.out.println("모든 운동리스트를 가져왔습니다.");
		return exerciseDao.selectAll();
	}
	
	// id로 운동 리스트 조회
	@Override
	public Exercise getExerciseListById(int exerciseId) {
		System.out.println("지정한 ID:"+exerciseId+"의 운동리스트를 가져왔습니다.");
		return exerciseDao.selectExerciseByExId(exerciseId);
	}
	
	// 부위별로 운동 리스트 조회
	@Override
	public List<Exercise> getExerciseListByParts(String exerciseParts) {
		System.out.println("지정한 부위:"+exerciseParts+"의 운동리스트를 가져왔습니다.");
		return exerciseDao.selectExerciseByParts(exerciseParts);
	}

}
