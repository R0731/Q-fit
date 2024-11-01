package com.qfit.mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qfit.mvc.model.dto.Task;
import com.qfit.mvc.model.service.TaskService;

import java.security.PublicKey;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/task")
public class TaskRestController {

	private final TaskService taskService;

	@Autowired
	public TaskRestController(TaskService taskService) {
		this.taskService = taskService;
	}
	//전체 조회 필요 없음? => 전체 조회에서 퀘스트 ID로 조건 걸어야 하나?
	//전체 조회
	@GetMapping("")
	public ResponseEntity<List<Task>> list(){
		List<Task> list = taskService.getTaskList();
		System.out.println(list.toString());
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	// 유산소 등록	
	// 이 부분 어렵다.. 유산소와 무산소 태스크를 나눈 것이 잘 못인가..
	//URL은 어떻게 처리해야 하지?
	@PostMapping("/add-cardio-task")
	public ResponseEntity<?> addCardio(@ModelAttribute Task taskDto){
		taskService.addCardioTask(taskDto);
		System.out.println(taskDto);
		return new ResponseEntity<Task>(taskDto, HttpStatus.CREATED);
		}
	
	//무산소 등록
	@PostMapping("/add-weight-task")
	public ResponseEntity<?> addWeight(@ModelAttribute Task taskDto){
		taskService.addWeightTask(taskDto);
		System.out.println(taskDto);
		return new ResponseEntity<Task>(taskDto, HttpStatus.CREATED);
	}
	
	//
	@DeleteMapping("/{taskId}")
	public ResponseEntity<String> delete(@PathVariable("taskId") int id){
		System.out.println(id);
		boolean isDeleted = taskService.deleteTask(id);
		
		if(isDeleted) {
			return ResponseEntity.status(HttpStatus.OK).body("Task deleted");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed");
	}
	
	@PutMapping("/cardio/{id}")
	public ResponseEntity<Void> updateCardio(@PathVariable("taskId") int id, @RequestBody Task taskDto){
		taskDto.setTaskId(id);
		taskService.updateCardioTask(taskDto);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PutMapping("/weight/{id}")
	public ResponseEntity<Void> updateWeight(@PathVariable("taskId") int id, @RequestBody Task taskDto){
		taskDto.setTaskId(id);
		taskService.updateWeightTask(taskDto);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateOrder(@PathVariable("taskId") int id, @RequestBody Task taskDto){
		taskDto.setTaskId(id);
		taskService.updateOrder(taskDto);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
	
