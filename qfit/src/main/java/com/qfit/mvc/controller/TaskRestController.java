package com.qfit.mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qfit.mvc.model.dto.Task;
import com.qfit.mvc.model.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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
@Tag(name="TaskRestful API", description = "태스크 CRUD")
public class TaskRestController {

	private final TaskService taskService;

	@Autowired
	public TaskRestController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	/**
	 * 태스크 전체 조회 메서드
	 * @param 없음
	 * @return 성공 시 OK(200)
	 */
	@GetMapping
	@Operation(summary = "태스크 전체조회", description = "모든 태스크를 조회합니다.")
	public ResponseEntity<List<Task>> list(){
		List<Task> list = taskService.getTaskList();
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	/**
	 * 퀘스트id로 태스크 조회 메서드
	 * @param questId
	 * @return 성공 시 OK(200)
	 */
	@GetMapping("/list/{questId}")
	@Operation(summary = "퀘스트 ID로 태스크 조회", description = "퀘스트ID로 태스크를 조회합니다.")
	public ResponseEntity<List<Task>> questList(@PathVariable("questId") int id){
		List<Task> list = taskService.getTaskListdByQuestId(id);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	/**
	 * 태스크 등록 메서드
	 * @param Task JSON, 유산소, 무산소에 따라 등록됨.
	 * @return 성공 시 CREATED (201), 잘못된 데이터 입력시,
	 *         실패 시 INTERNAL_SERVER_ERROR(500) 반환
	 */
	@PostMapping("")
	@Operation(summary = "태스크 등록", description = "태스크를 등록합니다.")
    public ResponseEntity<String> createTask(@RequestBody Task task) {
        try {
            taskService.createTask(task); // 태스크 생성 로직
            return new ResponseEntity<>("태스크가 성공적으로 생성되었습니다.", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	/**
	 * 태스크 삭제 메서드
	 * @param taskId
	 * @return 성공 시 OK (200),
	 *         실패 시 INTERNAL_SERVER_ERROR(500) 반환
	 */
	@DeleteMapping("/{taskId}")
	@Operation(summary = "태스크 삭제", description = "태스크를 삭제합니다.")
	public ResponseEntity<String> delete(@PathVariable("taskId") int id){
		boolean isDeleted = taskService.deleteTask(id);
		
		if(isDeleted) {
			return ResponseEntity.status(HttpStatus.OK).body("태스크가 삭제되었습니다.");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("태스크 삭제 실패");
	}
	
	/**
	 * 태스크 수정 메서드
	 * @param taskId JSON, 유산소, 무산소에 따라 수정됨.
	 * @return 성공 시 OK (200),
	 *         실패 시 INTERNAL_SERVER_ERROR(500) 반환
	 */
    @PutMapping("/{taskId}")
    @Operation(summary = "태스크 수정", description = "태스크를 수정합니다.")
    public ResponseEntity<String> updateTask(@PathVariable int taskId, @RequestBody Task task) {
        try {
            task.setTaskId(taskId);
            taskService.updateTask(task);
            return new ResponseEntity<>("태스크가 성공적으로 수정되었습니다.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
    /**
	 * 태스크 순서 메서드
	 * @param taskId JSON
	 * @return 성공 시 OK (200)
	 */
	@PutMapping("/order/{taskId}")
	@Operation(summary = "태스크 순서 수정", description = "태스크의 순서를 수정합니다.")
	public ResponseEntity<Void> updateOrder(@PathVariable("taskId") int id, @RequestBody Task task){
		task.setTaskId(id);
		taskService.updateOrder(task);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	/**
	 * 태스크 수행여부 수정 메서드
	 * @param taskId JSON
	 * @return 성공 시 OK (200),
	 *         실패 시 INTERNAL_SERVER_ERROR(500) 반환
	 */
	@PutMapping("/complete/{taskId}")
	@Operation(summary = "태스크 수행여부 수정", description = "태스크 수행여부를 수정합니다.")
	public ResponseEntity<String> updateTaskCompleted(@PathVariable int taskId, @RequestBody Task task) {
		try {
			task.setTaskId(taskId);
			taskService.updateTaskCompleted(task);
			return new ResponseEntity<>("태스크 수행여부가 성공적으로 수정되었습니다.", HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
	
