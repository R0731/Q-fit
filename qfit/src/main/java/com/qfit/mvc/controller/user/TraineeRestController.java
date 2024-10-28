package com.qfit.mvc.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qfit.mvc.model.dto.user.Trainee;
import com.qfit.mvc.model.dto.user.User;
import com.qfit.mvc.model.service.user.TraineeService;

@RestController
@RequestMapping("/qfit/trainee")
public class TraineeRestController {
	
	private final TraineeService traineeService;
	
	@Autowired
	public TraineeRestController(TraineeService traineeService) {
		this.traineeService = traineeService;
	}
	
	@PutMapping("/{traineeId}/trainer")
	public ResponseEntity<?> updateTrainer(@PathVariable(value="traineeId") int traineeId, @RequestBody Trainee trainee){
		System.out.println(traineeId);
		System.out.println(trainee.toString());
		
		boolean isUpdated = traineeService.updateTrainer(traineeId, trainee.getTrainerId());
			
		if(isUpdated) return ResponseEntity.status(HttpStatus.OK).body("Update Success");
			
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update Failed");
			
	}
	
	@DeleteMapping("/resign/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value="id") int id){
		boolean isDeleted = traineeService.resign(id);

		if(isDeleted) return ResponseEntity.status(HttpStatus.OK).body("Delete Success");
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
