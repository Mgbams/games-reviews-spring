package fr.orsys.gamesreviews.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import fr.orsys.gamesreviews.dto.GameDTO;
import fr.orsys.gamesreviews.service.GameService;

@ExtendWith(SpringExtension.class)
class GameControllerTest {
	@InjectMocks
    private GameController gameController;

    @Mock
    private GameService gameService;
	
	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	private ObjectWriter objectWriter = objectMapper.writer();
    
    @BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
	}
	
	@Test
	void get_game_by_id_success() {
		Long idGame = 1L;
		GameDTO gameDto = new GameDTO();
		gameDto.setId(idGame);
		
		ResponseEntity<GameDTO> expected = new ResponseEntity<>(gameDto, HttpStatus.OK);
		
		when(gameService.getById(idGame)).thenReturn(gameDto);
		
		ResponseEntity<GameDTO> result = gameController.getGameById(idGame);
		
		verify(gameService).getById(idGame);
		
		assertEquals(expected, result);
		
	}
	
	
	@Test
	void add_game() {
		Long idGame = 1L;
		
		GameDTO newGame = new GameDTO();
		
		newGame.setId(idGame);
		
        ResponseEntity<GameDTO> expected = new ResponseEntity<>(newGame, HttpStatus.CREATED);
		
		when(gameService.add(any(GameDTO.class))).thenReturn(newGame);
		
		ResponseEntity<GameDTO> result = gameController.addGame(newGame);
		
		verify(gameService).add(newGame);
		
		assertEquals(expected, result);
		
	}
	
	@Test
	void update_game() {
		Long idGame = 2L;
		GameDTO newGame = new GameDTO();
		newGame.setId(2L);
		
		 ResponseEntity<GameDTO> expected = new ResponseEntity<>(newGame, HttpStatus.OK);
			
			when(gameService.update(idGame, newGame)).thenReturn(newGame);
			
			ResponseEntity<GameDTO> result = gameController.updateGame(newGame, idGame);
			
			verify(gameService).update(idGame, newGame);
			
			assertEquals(expected, result);
				
	}
	
	@Test
	void delete_game() throws Exception {
		Long idGame = 1L;
		GameDTO gameToDelete = new GameDTO();
		gameToDelete.setId(idGame);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/games/{id}", idGame).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
		
		verify(gameService, times(1)).deleteById(idGame);
		
	}

}
