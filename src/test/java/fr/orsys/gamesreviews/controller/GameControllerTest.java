package fr.orsys.gamesreviews.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import fr.orsys.gamesreviews.dto.GameDTO;
import fr.orsys.gamesreviews.service.BusinessModelService;
import fr.orsys.gamesreviews.service.ClassificationService;
import fr.orsys.gamesreviews.service.GameService;
import fr.orsys.gamesreviews.service.GenreService;
import fr.orsys.gamesreviews.service.PlatformService;
import fr.orsys.gamesreviews.service.PublisherService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GameControllerTest {
	@InjectMocks
    private GameController gameController;

    @Mock
    private GameService gameService;
    
    private MockMvc mockMvc;
	private AutoCloseable closeable;

	private ObjectMapper objectMapper = new ObjectMapper();
	private ObjectWriter objectWriter = objectMapper.writer();
	
	@Captor
	private ArgumentCaptor<GameDTO> argumentCaptor;
    
	@Autowired
	private GenreService genreService;
	
	@Autowired
	private BusinessModelService businessModelServie;
	
	@Autowired
	private ClassificationService classificationService;
	
	@Autowired
	private PublisherService publisherService;
	
	@Autowired
	private PlatformService platformService;
	
	@BeforeEach
	public void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
	}
	
	@Test
	public void createGame_success() throws Exception {
		Long idGenre = 1L;
		Long idBusinessModel = 1L;
		Long idClassification = 1L;
		Long idPublisher = 1L;
		Long idPlatform = 1L;
		
		GameDTO newGame = new GameDTO();
		newGame.setPlatforms(new ArrayList<>());
		
		newGame.setId(2L);
		newGame.setName("X-men");
		newGame.setGenre(genreService.getById(idGenre));
		newGame.setBusinessModel(businessModelServie.getById(idBusinessModel));
		newGame.setClassification(classificationService.getById(idClassification));
		newGame.setDescription("A new game of life");
		newGame.setPicture("tri.jpg");
		newGame.setReleaseDate(new Date(17/11/2001));
		newGame.setPublisher(publisherService.getById(idPublisher));
		newGame.getPlatforms().add(platformService.getPlatformById(idPlatform));
		
		when(gameService.addGame(newGame)).thenReturn(newGame);

		String content = objectWriter.writeValueAsString(newGame);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/games")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);

		mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isCreated());
			
		
		assertEquals("X-men", newGame.getName());
		assertEquals(2, newGame.getId());
		assertEquals(idGenre, newGame.getGenre().getId());
		assertEquals(idBusinessModel, newGame.getBusinessModel().getId());
		assertEquals(idClassification, newGame.getClassification().getId());
		assertEquals(idPublisher, newGame.getPublisher().getId());

	}
	
	/*@Test
	public void get_game_by_id_success() throws Exception {
		Long idGenre = 1L;
		when(gameService.getGameById(null)).thenReturn(java.util.Optional.of(RECORD_1));

		mockMvc.perform(MockMvcRequestBuilders.get("/book/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.bookId").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Atomic Habits"));
	}*/
	
	@AfterEach
	public void closeService() throws Exception {
		closeable.close();
	}
}
