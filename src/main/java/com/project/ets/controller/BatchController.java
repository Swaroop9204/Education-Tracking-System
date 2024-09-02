package com.project.ets.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.ets.exception.BatchNotFoundByIdException;
import com.project.ets.requstdto.BatchRequest;
import com.project.ets.responsedto.BatchResponse;
import com.project.ets.service.BatchService;
import com.project.ets.util.AppResponseBuilder;
import com.project.ets.util.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BatchController {
	private BatchService batchService;
	private AppResponseBuilder responseBuilder;
	
	
	@Operation(description = "This end point is used to register the batches to the database",responses = {
			@ApiResponse(responseCode = "201",description = "batch is created successfully"),
			@ApiResponse(responseCode = "500",description = "internal server error",content = {@Content(schema = @Schema(anyOf = RuntimeException.class))})
	})
	@PostMapping("/batches")
	public ResponseEntity<ResponseStructure<BatchResponse>> saveBatch(@RequestBody BatchRequest request){
		BatchResponse response = batchService.saveBatch(request);
		return responseBuilder.success(HttpStatus.CREATED, "batch created successfully", response);
	}
	
	@Operation(description = "This end point is used to update the batch",responses = {
			@ApiResponse(responseCode = "200",description = "batch is updated successfully"),
			@ApiResponse(responseCode = "404",description = "batch not found by given id",content = {@Content(schema = @Schema(anyOf = BatchNotFoundByIdException.class))})
	})
	@PutMapping("/batches/{batchId}")
	public ResponseEntity<ResponseStructure<BatchResponse>> updateBatch(@RequestBody BatchRequest request,@PathVariable String batchId){
		BatchResponse response=batchService.updateBatch(request,batchId);
		return responseBuilder.success(HttpStatus.OK, "batch updated successfully", response);
	}
	
	@Operation(description = "This end point is used to update the batch status to cancel state to the database",responses = {
			@ApiResponse(responseCode = "200",description = "batch is updated successfully"),
			@ApiResponse(responseCode = "404",description = "batch not found by given id",content = {@Content(schema = @Schema(anyOf = BatchNotFoundByIdException.class))})
	})
	@PatchMapping("/batches/{batchId}/cancel")
	public ResponseEntity<ResponseStructure<BatchResponse>> cancelBatch(@PathVariable String batchId){
		BatchResponse response=batchService.cancelBatch(batchId);
		return responseBuilder.success(HttpStatus.OK, "batch updated successfully", response);
	
	}
	
	@Operation(description = "This end point is used to update the batch status to close state to the database",responses = {
			@ApiResponse(responseCode = "200",description = "batch is updated successfully"),
			@ApiResponse(responseCode = "404",description = "batch not found by given id",content = {@Content(schema = @Schema(anyOf = BatchNotFoundByIdException.class))})
	})
	@PatchMapping("/batches/{batchId}/close")
	public ResponseEntity<ResponseStructure<BatchResponse>> closeBatch(@PathVariable String batchId){
		BatchResponse response=batchService.cancelBatch(batchId);
		return responseBuilder.success(HttpStatus.OK, "batch updated successfully", response);
	}

}
