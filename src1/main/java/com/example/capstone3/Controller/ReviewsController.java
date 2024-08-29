package com.example.capstone3.Controller;

import com.example.capstone3.Api.ApiResponse;
import com.example.capstone3.Model.Reviews;
import com.example.capstone3.Service.ReviewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewsController {
    private final ReviewsService reviewsService;

    @GetMapping("/get")
    public ResponseEntity getReviews(){
        return ResponseEntity.status(200).body(reviewsService.getAllReviews());
    }
    @PostMapping("/add")
    public ResponseEntity addReviews(@Valid @RequestBody Reviews reviews){

        reviewsService.addReviews(reviews);
        return ResponseEntity.status(200).body(new ApiResponse("Reviews added successfully!"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateReviews(@PathVariable Integer id,@Valid @RequestBody Reviews reviews){

        reviewsService.updateReviews(id,reviews);
        return ResponseEntity.status(200).body(new ApiResponse("Reviews updated successfully!"));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteReviews(@PathVariable Integer id){
        reviewsService.deleteReviews(id);
        return ResponseEntity.status(200).body(new ApiResponse("Reviews deleted successfully!"));
    }

    @PutMapping("/assign/{review_id}/{consultant_id}")
    public ResponseEntity assignReviewsToConsultant (@PathVariable Integer review_id, @PathVariable Integer consultant_id ){
        reviewsService.assignReviewsToConsultant(review_id,consultant_id);
        return ResponseEntity.status(200).body(new ApiResponse("Reviews Assigned to Consultant Successfully"));
    }

    //assign Order ToReview
    @PutMapping("/assignOrderToReview/{orderId}/{revId}")
    public ResponseEntity assignOrderToReview(@PathVariable Integer orderId,@PathVariable Integer revId ){
        reviewsService.assignOrderToReview(orderId,revId);
        return ResponseEntity.status(200).body(new ApiResponse("Reviews Assigned to Order Successfully"));
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity getAllReviewsForOrder(@PathVariable Integer orderId) {
        return ResponseEntity.status(200).body(reviewsService.getAllReviewsForOrder(orderId));
    }
    @GetMapping("/getByRating/{rating}")
    public ResponseEntity getReviewsByRating(@PathVariable Integer rating){
        return ResponseEntity.status(200).body(reviewsService.getAllReviewsByRating(rating));
    }

}
