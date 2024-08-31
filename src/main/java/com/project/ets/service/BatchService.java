package com.project.ets.service;


import org.springframework.stereotype.Service;

import com.project.ets.entity.Batch;
import com.project.ets.enums.BatchStatus;
import com.project.ets.exception.BatchNotFoundByIdException;
import com.project.ets.mapper.BatchMapper;
import com.project.ets.repository.BatchRepository;
import com.project.ets.requstdto.BatchRequest;
import com.project.ets.responsedto.BatchResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BatchService {
	private BatchRepository batchRepository;
	private BatchMapper mapper;
	
	public BatchResponse saveBatch(BatchRequest request) {
		Batch batch = mapper.mapToBatchEntity(request, new Batch());
		batch.setBatchStatus(BatchStatus.CREATED);;
		batch=batchRepository.save(batch);
		return mapper.mapToBatchResponse(batch);
	}

	public BatchResponse updateBatch(BatchRequest request, String batchId) {
		return batchRepository.findById(batchId).map(batch->{
			batch=mapper.mapToBatchEntity(request, batch);
			batch=batchRepository.save(batch);
			return mapper.mapToBatchResponse(batch);
			
		}).orElseThrow(()->new BatchNotFoundByIdException("failed to update the batch"));
	}

	public BatchResponse cancelBatch(String batchId) {
		return batchRepository.findById(batchId).map(batch->{
			batch.setBatchStatus(BatchStatus.CANCELLED);
			batch=batchRepository.save(batch);
			return mapper.mapToBatchResponse(batch);
			
		}).orElseThrow(()->new BatchNotFoundByIdException("failed to change the batch status"));
	}
	public BatchResponse closeBatch(String batchId) {
		return batchRepository.findById(batchId).map(batch->{
			batch.setBatchStatus(BatchStatus.CLOSED);
			batch=batchRepository.save(batch);
			return mapper.mapToBatchResponse(batch);
			
		}).orElseThrow(()->new BatchNotFoundByIdException("failed to change the batch status"));
	}
	
	
	
	

}
