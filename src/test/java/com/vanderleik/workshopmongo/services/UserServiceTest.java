package com.vanderleik.workshopmongo.services;

import com.vanderleik.workshopmongo.domain.User;
import com.vanderleik.workshopmongo.dto.UserDTO;
import com.vanderleik.workshopmongo.repository.UserRepository;
import com.vanderleik.workshopmongo.services.exception.ObjectNotFoundException;
import com.vanderleik.workshopmongo.utils.TranslationsConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository repo;
    private User user;
    private UserDTO objDto;

    @BeforeEach
    void setUp(){
        user = new User();
        user.setId("1");
        user.setName("Vanderlei");
        user.setEmail("vanderlei@email.com");

        objDto = new UserDTO();
        objDto.setId("123");
        objDto.setName("Objeto DTO");
        objDto.setEmail("objeto@dto.com");
    }

    @Test
    void testFindByIdNaoDeveRetornarExcecao(){
        Mockito.when(repo.findById(Mockito.anyString())).thenReturn(Optional.of(user));

        User userRetornado = assertDoesNotThrow(() -> userService.findById(user.getId()));
        assertEquals(user.getId(), userRetornado.getId());
    }

    @Test
    void testFindByIdDeveRetornarExcecao(){
        Mockito.when(repo.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(null));

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> userService.findById(user.getId()));
        assertEquals(TranslationsConstants.OBJETO_NAO_ENCONTRADO, ex.getMessage());
    }

    @Test
    void testFindAll(){
        User user1 = new User();
        user1.setId("1");
        user1.setName("Usuario Um");
        user1.setEmail("UsuarioUm@email.com");

        User user2 = new User();
        user2.setId("2");
        user2.setName("Usuario Dois");
        user2.setEmail("UsuarioDois@email.com");

        User user3 = new User();
        user3.setId("3");
        user3.setName("Usuario Três");
        user3.setEmail("UsuarioTres@email.com");

        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        list.add(user3);

        Mockito.when(repo.findAll()).thenReturn(list);

        List<User> users = assertDoesNotThrow(() -> userService.findAll());
        assertNotNull(users);
        for (int i = 0; i < users.size(); i++) {
            assertEquals(list.get(i).getId(), users.get(i).getId());
        }
    }

    @Test
    void testInsert(){
        assertDoesNotThrow(() -> userService.insert(user));
        Mockito.verify(repo).insert(user);
    }

    @Test
    void testInsertObjNull(){
        user = null;

        User userInserido = assertDoesNotThrow(() -> userService.insert(user));
        assertNull(userInserido);
    }

    @Test
    void testFromDTO(){
        User user = assertDoesNotThrow(() -> userService.fromDTO(objDto));
        assertEquals(objDto.getId(), user.getId().toString());
        assertEquals(objDto.getName(), user.getName());
        assertEquals(objDto.getEmail(), user.getEmail());
    }

    @Test
    void testFromDTONulo(){
        objDto = null;

        User user = assertDoesNotThrow(() -> userService.fromDTO(objDto));
        assertNull(user);
    }

    @Test
    void testDelete(){
        Mockito.when(repo.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> userService.delete(user.getId()));

        Mockito.when(repo.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> userService.delete(user.getId()));
        assertEquals(TranslationsConstants.OBJETO_NAO_ENCONTRADO, ex.getMessage());
    }
}