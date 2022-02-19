package fr.orsys.gamesreviews.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.orsys.gamesreviews.business.user.Moderator;
import fr.orsys.gamesreviews.business.user.Player;
import fr.orsys.gamesreviews.business.user.User;
import fr.orsys.gamesreviews.dto.GameDTO;
import fr.orsys.gamesreviews.dto.UserDTO;
import fr.orsys.gamesreviews.exception.RecordNotFoundException;
import fr.orsys.gamesreviews.mapper.Mapper;
import fr.orsys.gamesreviews.repository.UserRepository;
import fr.orsys.gamesreviews.service.impl.UserServiceImpl;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
	
	@InjectMocks
	private UserServiceImpl userServiceImpl;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private Mapper<User, UserDTO> mapper;
	
	@Test
	void should_existing_by_pseudonym() {
		String pseudonym = "testPseudo";
		when(userRepository.existsByPseudonym(pseudonym)).thenReturn(true);
		
		boolean result = userServiceImpl.existsByPseudonym(pseudonym);
		
		assertTrue(result);
	}
	
	@Test
	void should_existing_by_email() {
		String pseudonym = "test@test.test";
		when(userRepository.existsByEmail(pseudonym)).thenReturn(true);
		
		boolean result = userServiceImpl.existsByEmail(pseudonym);
		
		assertTrue(result);
	}
	
    @Test
    void should_Throw_IllegalArgumentException_When_FindById_And_Pseudonym_null() {
        assertThrows(IllegalArgumentException.class, () -> userServiceImpl.findByPseudonym(null));
    }
    
    // TODO : REGARDER SI CEST POSSIBLE DE METTRE LES DEUX CLASSES DANS LES VALUESOURCES POUR EVITER
    // 		  DE REFAIRE UN DEUXIEME TEST
    
//    @ParameterizedTest
//    @ValueSource(classes = {Player.class, Moderator.class} )
//    void should_Return_BusinessModel_When_FindByPseudonym(Class<? extends User> user) {
        // Arrange
//        String pseudo = "pseudonym";
//        String pseudo2 = "pseudonym2";
//        User user = new Player();
//        User user2 = new Moderator();
        
        
//        user.setPseudonym(pseudo);
//        user2.setPseudonym(pseudo2);
        

//        UserDTO expected = new UserDTO();
//        expected.setPseudonym();
        
//        UserDTO expected2 = new UserDTO();
//        expected2.setPseudonym(pseudo2);

//        when(userRepository.findByPseudonym(any(String.class))).thenReturn(Optional.of(user));
//        when(userRepository.findByPseudonym(any(String.class))).thenReturn(Optional.of(user2));
//        when(mapper.mapEntityToDto(any(User.class))).thenReturn(expected);
//        when(mapper.mapEntityToDto(any(User.class))).thenReturn(expected2);

        // Act
//        UserDTO result = userServiceImpl.findByPseudonym(pseudo);
//        UserDTO result2 = userServiceImpl.findByPseudonym(pseudo);
        

        // Assert
//        verify(userRepository).findByPseudonym(pseudo);
//
//        verify(mapper).mapEntityToDto(user);
//
//        assertEquals(expected, result);
//        assertEquals(expected2, result2);
//    }
    
    @Test
    void should_Return_BusinessModel_When_FindByPseudonym_For_Player() {
    	String pseudo = "pseudonym";
    	User user = new Player();
    	user.setPseudonym(pseudo);

        UserDTO expected = new UserDTO();
        expected.setPseudonym(pseudo);
        
        when(userRepository.findByPseudonym(any(String.class))).thenReturn(Optional.of(user));
        when(mapper.mapEntityToDto(any(User.class))).thenReturn(expected);
        UserDTO result = userServiceImpl.findByPseudonym(pseudo);
        
        verify(userRepository).findByPseudonym(pseudo);

        verify(mapper).mapEntityToDto(user);

        assertEquals(expected, result);
    }
    
    @Test
    void should_Return_BusinessModel_When_FindByPseudonym_For_Moderator() {
    	String pseudo = "pseudonym";
    	User user = new Moderator();
    	user.setPseudonym(pseudo);

        UserDTO expected = new UserDTO();
        expected.setPseudonym(pseudo);
        
        when(userRepository.findByPseudonym(any(String.class))).thenReturn(Optional.of(user));
        when(mapper.mapEntityToDto(any(User.class))).thenReturn(expected);
        UserDTO result = userServiceImpl.findByPseudonym(pseudo);
        
        verify(userRepository).findByPseudonym(pseudo);

        verify(mapper).mapEntityToDto(user);

        assertEquals(expected, result);
    }
    
    @Test

    void should_Throw_RecordNotFoundException_When_FindByPseudonyme() {
        String pseudo = "pseudonym";
        Player user = new Player();
        user.setPseudonym(pseudo);

        
        when(userRepository.findByPseudonym(pseudo)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> userServiceImpl.findByPseudonym(pseudo));
    }
    
}
