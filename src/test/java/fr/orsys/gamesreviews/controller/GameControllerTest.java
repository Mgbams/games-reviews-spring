package fr.orsys.gamesreviews.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.orsys.gamesreviews.dto.GameDTO;
import fr.orsys.gamesreviews.service.GameService;

@ExtendWith(SpringExtension.class)
class GameControllerTest {
	@InjectMocks
    private GameController gameController;

    @Mock
    private GameService gameService;
	
	@Test
	void get_game_by_id_success() throws Exception {
		Long idGame = 1L;
		GameDTO gameDto = new GameDTO();
		gameDto.setId(idGame);
		
		ResponseEntity<GameDTO> expected = new ResponseEntity<>(gameDto, HttpStatus.OK);
		
		when(gameService.getById(idGame)).thenReturn(gameDto);
		
		ResponseEntity<GameDTO> result = gameController.getGameById(idGame);
		
		verify(gameService).getById(idGame);
		
		assertEquals(expected, result);
		
		
	}

}
