package com.project.ets.mapper;

import org.springframework.stereotype.Component;

import com.project.ets.entity.Batch;
import com.project.ets.requstdto.BatchRequest;
import com.project.ets.responsedto.BatchResponse;
@Component
public class BatchMapper {
	
	public Batch mapToBatchEntity(BatchRequest request,Batch batch) {
		batch.setTitle(request.getTitle());
		batch.setSubjects(request.getSubjects());
		batch.setBeginsAt(request.getBeginsAt());
		batch.setEndsAt(request.getEndsAt());
		batch.setStartingDate(request.getStartingDate());
		return batch;
	}
	
	public BatchResponse mapToBatchResponse(Batch batch) {
		BatchResponse response=new BatchResponse();
		response.setBatchId(batch.getBatchId());
		response.setTitle(batch.getTitle());
		response.setSubjects(batch.getSubjects());
		response.setStartingDate(batch.getStartingDate());
		response.setBeginsAt(batch.getBeginsAt());
		response.setEndsAt(batch.getEndsAt());
		
		return response;
		
	}

}
