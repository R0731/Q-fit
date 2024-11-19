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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/exercise")
@Tag(name="ExcerciseRestful API", description = "운동 CRUD")
public class ExerciseRestController {
	
	private final ExerciseService exerciseService;
	
	@Autowired
	public ExerciseRestController(ExerciseService exerciseService) {
		this.exerciseService = exerciseService;
	}
	
	/**
	 * 운동목록 전체 조회 메서드
	 * @param 없음
	 * @return 성공 시 OK (200)
	 */
	@GetMapping("/list")
	@Operation(summary = "운동 전체조회", description = "모든 운동을 조회합니다.")
	public ResponseEntity<List<Exercise>> list(){
		List<Exercise> list = exerciseService.getExerciseList();
		System.out.println(list.toString());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	/**
	 * id로 운동 조회 메서드
	 * @param id
	 * @return 성공 시 OK (200),
	 *         실패 시 NOT_FOUND(404) 반환
	 */
	@GetMapping("/list/{id}")
	@Operation(summary = "id로 운동 조회", description = "ID로 운동을 조회합니다.")
	public ResponseEntity<Exercise> detail(@PathVariable("id") int id){
		Exercise exercise = exerciseService.getExerciseListById(id);
		
		if(exercise != null) {
			System.out.println(exercise.toString());
			return new ResponseEntity<>(exercise, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	/**
	 * 운동부위별로 exercise 조회 메서드
	 * @param parts (string)
	 * @return 성공 시 OK (200)
	 */
	@GetMapping("/list/parts")
	@Operation(summary = "운동부위별 운동조회", description = "운동부위별로 운동을 조회합니다.")
	public ResponseEntity<List<Exercise>> parts(@RequestParam String parts){
		System.out.println(parts);
		List<Exercise> list = exerciseService.getExerciseListByParts(parts);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
}
