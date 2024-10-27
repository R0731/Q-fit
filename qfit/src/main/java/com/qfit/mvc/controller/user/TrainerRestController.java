package com.qfit.mvc.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qfit.mvc.model.dto.user.Trainer;
import com.qfit.mvc.model.dto.user.User;
import com.qfit.mvc.model.service.user.TrainerService;

@RestController
@RequestMapping("/qfit/trainer")
public class TrainerRestController {
	
	private final TrainerService trainerService;
	
	@Autowired
	public TrainerRestController(TrainerService trainerService) {
		this.trainerService = trainerService;
	}
	
	@PutMapping("/{trainerId}/gym")
	public ResponseEntity<String> updateGym(@PathVariable("trainerId") int trainerId, @RequestBody Trainer trainer){
		boolean isUpdated = trainerService.updateGym(trainerId, trainer.getGym());
		
		if(isUpdated) return ResponseEntity.status(HttpStatus.OK).body("Update Success");
			
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update Failed");
	}
	
	@DeleteMapping("/resign/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value="id") int id){
		boolean isDeleted = trainerService.resign(id);

		if(isDeleted) return ResponseEntity.status(HttpStatus.OK).body("Delete Success");
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
