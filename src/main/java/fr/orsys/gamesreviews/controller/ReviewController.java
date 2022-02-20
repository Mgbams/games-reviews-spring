package fr.orsys.gamesreviews.controller;

import fr.orsys.gamesreviews.dto.ReviewDTO;
import fr.orsys.gamesreviews.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor

@Validated
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<Page<ReviewDTO>> getValidatedReviews(
            @RequestParam(value = "gameId", required = false) Long id,
            @PageableDefault(size = 5) Pageable pageable) {
        Page<ReviewDTO> reviews = id == null
                ? reviewService.getValidatedReviews(pageable)
                : reviewService.getValidatedReviewsByGame(id, pageable);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/pending")
    public ResponseEntity<Page<ReviewDTO>> getPendingReviews(@PageableDefault Pageable pageable) {
        return new ResponseEntity<>(reviewService.getPendingReviews(pageable), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ReviewDTO> addReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        return new ResponseEntity<>(reviewService.add(reviewDTO), HttpStatus.CREATED);
    }

    @PutMapping("/validate")
    public ResponseEntity<ReviewDTO> validateReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        return new ResponseEntity<>(reviewService.validate(reviewDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteReview(@PathVariable Long id) {
        reviewService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
