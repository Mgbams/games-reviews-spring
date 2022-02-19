package fr.orsys.gamesreviews.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.orsys.gamesreviews.dto.ReviewDTO;
import fr.orsys.gamesreviews.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class ReviewControllerTest {

    private static final String REVIEWS_URL = "/api/reviews";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Test
    void should_Return_ResponseEntity_With_Page_ReviewsDTO_And_Status_OK_When_getValidatedReviews() throws Exception {
        Page<ReviewDTO> reviews = Page.empty();
        given(reviewService.getValidatedReviews(any(Pageable.class))).willReturn(reviews);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .get(REVIEWS_URL)
                        .param("page", "0")
                        .param("size", "5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> new ResponseEntity<>(reviews, HttpStatus.OK))
                .andReturn().getResponse();

        // Unnecessary method. Just shows how to test differently
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void should_Return_ResponseEntity_With_Page_ReviewsDTO_And_Status_OK_When_getValidatedReviews_And_gameID_Param()
            throws Exception {
        Page<ReviewDTO> reviews = Page.empty();
        given(reviewService.getValidatedReviewsByGame(any(Long.class), any(Pageable.class))).willReturn(reviews);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(REVIEWS_URL)
                        .param("gameId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> new ResponseEntity<>(reviews, HttpStatus.OK));
    }

    @Test
    void should_Return_ResponseEntity_With_Page_ReviewsDTO_And_Status_OK_When_getPendingReviews() throws Exception {
        Page<ReviewDTO> reviews = Page.empty();
        given(reviewService.getPendingReviews(any(Pageable.class))).willReturn(reviews);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(REVIEWS_URL + "/pending")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> new ResponseEntity<>(reviews, HttpStatus.OK));
    }

    @Test
    void should_Return_ResponseEntity_With_ReviewDTO_And_Status_CREATED_When_addReview() throws Exception {
        ReviewDTO review = new ReviewDTO();
        given(reviewService.add(any(ReviewDTO.class))).willReturn(review);

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(REVIEWS_URL + "/add")
                        .content(mapper.writeValueAsString(review))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(result -> new ResponseEntity<>(review, HttpStatus.CREATED))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void should_Return_ResponseEntity_With_ReviewDTO_And_Status_OK_When_validateReview() throws Exception {
        ReviewDTO review = new ReviewDTO();
        given(reviewService.validate(any(Long.class), any(Long.class))).willReturn(review);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(REVIEWS_URL + "/validate")
                        .param("reviewId", "1")
                        .param("moderatorId", "1")
                )
                .andExpect(status().isAccepted())
                .andExpect(result -> new ResponseEntity<>(review, HttpStatus.ACCEPTED));
    }

    @Test
    void should_Return_ResponseEntity_With_String_Success_Message_And_Status_OK_When_deleteReview() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(REVIEWS_URL + "/1"))
                .andExpect(status().isAccepted())
                .andExpect(result -> new ResponseEntity<>(HttpStatus.ACCEPTED));
    }

}
