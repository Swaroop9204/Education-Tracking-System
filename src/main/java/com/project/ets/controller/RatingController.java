package com.project.ets.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.ets.requstdto.RatingRequest;
import com.project.ets.responsedto.RatingResponse;
import com.project.ets.service.RatingService;
import com.project.ets.util.AppResponseBuilder;
import com.project.ets.util.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class RatingController {
	private RatingService ratingService;
	private AppResponseBuilder responseBuilder;
	
	@PutMapping("/ratings/{ratingId}")
	public ResponseEntity<ResponseStructure<RatingResponse>> updateStudentRating(@RequestBody @Valid RatingRequest request,@PathVariable String ratingId){
		RatingResponse ratingResponse = ratingService.updateStudentRating(request,ratingId);
		return responseBuilder.success(HttpStatus.OK, "rating updated successfully", ratingResponse);
	}

}
