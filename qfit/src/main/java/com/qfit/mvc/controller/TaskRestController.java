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
	@GetMapping
	public ResponseEntity<List<Task>> list(){
		List<Task> list = taskService.getTaskList();
		System.out.println(list.toString());
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	// 태스크 등록	
	@PostMapping
    public ResponseEntity<String> createTask(@RequestBody Task task) {
        try {
            taskService.createTask(task); // 태스크 생성 로직
            return new ResponseEntity<>("태스크가 성공적으로 생성되었습니다.", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
	
	// 태스크 삭제
	@DeleteMapping("/{taskId}")
	public ResponseEntity<String> delete(@PathVariable("taskId") int id){
		System.out.println(id);
		boolean isDeleted = taskService.deleteTask(id);
		
		if(isDeleted) {
			return ResponseEntity.status(HttpStatus.OK).body("태스크가 삭제되었습니다.");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed");
	}
	
	// 태스크 수정
    @PutMapping("/{taskId}")
    public ResponseEntity<String> updateTask(@PathVariable int taskId, @RequestBody Task task) {
        try {
            task.setTaskId(taskId);
            taskService.updateTask(task);
            return new ResponseEntity<>("태스크가 성공적으로 수정되었습니다.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
	
    //순서 수정
	@PutMapping("/order/{taskId}")
	public ResponseEntity<Void> updateOrder(@PathVariable("taskId") int id, @RequestBody Task taskDto){
		taskDto.setTaskId(id);
		taskService.updateOrder(taskDto);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
	
