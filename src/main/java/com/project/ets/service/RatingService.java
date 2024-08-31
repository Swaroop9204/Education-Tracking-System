package com.project.ets.service;

import org.springframework.stereotype.Service;

import com.project.ets.exception.RatingNotFoundByIdException;
import com.project.ets.mapper.RatingMapper;
import com.project.ets.repository.RatingRepository;
import com.project.ets.requstdto.RatingRequest;
import com.project.ets.responsedto.RatingResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RatingService {
	
	private RatingRepository ratingRepository;
	private RatingMapper ratingMapper;

	

	public RatingResponse updateStudentRating(RatingRequest request, String ratingId) {
		return ratingRepository.findById(ratingId).map(rating->{
			rating=ratingMapper.mapToRatingEntity(request, rating);
			rating=ratingRepository.save(rating);
			return ratingMapper.mapToRatingResponseEntity(rating);
			}).orElseThrow(()->new RatingNotFoundByIdException("failed to update student rating"));
		
	}

}
