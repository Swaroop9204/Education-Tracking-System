package com.project.ets.mapper;

import org.springframework.stereotype.Component;

import com.project.ets.entity.Rating;
import com.project.ets.requstdto.RatingRequest;
import com.project.ets.responsedto.RatingResponse;

@Component
public class RatingMapper {
	
	public Rating mapToRatingEntity(RatingRequest ratingRequest,Rating rating) {
		rating.setRating(ratingRequest.getRating());
		rating.setFeedback(ratingRequest.getFeedback());
		return rating;
	}
	
	public RatingResponse mapToRatingResponseEntity(Rating rating) {
		RatingResponse response=new RatingResponse();
		response.setRatingId(rating.getRatingId());
		response.setSubject(rating.getSubject());
		response.setRating(rating.getRating());
		response.setFeedback(rating.getFeedback());
	
		return response;
	}

}
