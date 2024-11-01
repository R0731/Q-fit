package com.qfit.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qfit.mvc.model.dto.Exercise;
import com.qfit.mvc.model.service.ExerciseService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/exercise")
public class ExerciseRestController {
	
	// 서비스 의존성 추가
	private final ExerciseService exerciseService;
	
	@Autowired
	public ExerciseRestController(ExerciseService exerciseService) {
		this.exerciseService = exerciseService;
	}
	
	// 전체 운동 조회
	@GetMapping("/list")
	public ResponseEntity<List<Exercise>> list(){
		List<Exercise> list = exerciseService.getExerciseList();
		System.out.println(list.toString());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 운동id로 운동 조회 => 왜 전체조회랑 다르게 처리하지?
	@GetMapping("/list/{id}")
	public ResponseEntity<Exercise> detail(@PathVariable("id") int id){
		Exercise exercise = exerciseService.getExerciseListById(id);
		
		if(exercise != null) {
//			return ResponseEntity.ok(exercise);
			return new ResponseEntity<>(exercise, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	// 운동부위별로 exercise 조회
	@GetMapping("/list/parts")
	public ResponseEntity<List<Exercise>> parts(@RequestParam String parts){
		System.out.println(parts);
		List<Exercise> list = exerciseService.getExerciseListByParts(parts);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
}
