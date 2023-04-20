package com.vanderleik.workshopmongo.services;

import com.vanderleik.workshopmongo.domain.Post;
import com.vanderleik.workshopmongo.domain.User;
import com.vanderleik.workshopmongo.dto.AuthorDTO;
import com.vanderleik.workshopmongo.repository.PostRepository;
import com.vanderleik.workshopmongo.services.exception.ObjectNotFoundException;
import com.vanderleik.workshopmongo.utils.TranslationsConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService service;
    @Mock
    private PostRepository repo;

    @Test
    void testFindByIdEncontrado(){
        User user = new User("1", "Nome do autor", "email@email.com.br");
        AuthorDTO author = new AuthorDTO(user);
        Post post = new Post("11", new Date(), "Título da postagem", "String body", author);

        when(repo.findById(post.getId())).thenReturn(Optional.of(post));
        Post postEncontrado = assertDoesNotThrow(() -> service.findById(post.getId()));

        assertEquals(post.getId(), postEncontrado.getId());
        assertEquals(post.getDate(), postEncontrado.getDate());
        assertEquals(post.getTitle(), postEncontrado.getTitle());
        assertEquals(post.getBody(), postEncontrado.getBody());
        assertEquals(post.getAuthor(), postEncontrado.getAuthor());
    }

    @Test
    void testFindByIdNaoEncontrado(){
        User user = new User("1", "Nome do autor", "email@email.com.br");
        AuthorDTO author = new AuthorDTO(user);
        Post post = new Post("11", new Date(), "Título da postagem", "String body", author);

        when(repo.findById(post.getId())).thenReturn(Optional.ofNullable(null));
        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> service.findById(post.getId()));

        assertEquals(TranslationsConstants.OBJETO_NAO_ENCONTRADO, ex.getMessage());
    }

    @Test
    void testFindAll(){
        User user = new User("1", "Nome do autor", "email@email.com.br");
        AuthorDTO author = new AuthorDTO(user);
        Post post1 = new Post("11", new Date(), "Título da postagem 1", "String body 1", author);
        Post post2 = new Post("12", new Date(), "Título da postagem 2", "String body 2", author);

        List<Post> list = new ArrayList<>();
        list.add(post1);
        list.add(post2);

        when(repo.findAll()).thenReturn(list);
        List<Post> postsEncontrados = assertDoesNotThrow(() -> service.findAll());

        assertNotNull(postsEncontrados);
        for (int i = 0; i < postsEncontrados.size(); i++) {
            assertEquals(list.get(i).getId(), postsEncontrados.get(i).getId());
            assertEquals(list.get(i).getDate(), postsEncontrados.get(i).getDate());
            assertEquals(list.get(i).getTitle(), postsEncontrados.get(i).getTitle());
            assertEquals(list.get(i).getBody(), postsEncontrados.get(i).getBody());
            assertEquals(list.get(i).getAuthor(), postsEncontrados.get(i).getAuthor());
        }
    }

    @Test
    void testFindTitle(){
        User user = new User("1", "Nome do autor", "email@email.com.br");
        AuthorDTO author = new AuthorDTO(user);
        Post post1 = new Post("11", new Date(), "Título da postagem 1", "String body 1", author);
        Post post2 = new Post("12", new Date(), "Título da postagem 2", "String body 2", author);

        List<Post> list = new ArrayList<>();
        list.add(post1);
        list.add(post2);

        when(repo.searchTitle(Mockito.anyString())).thenReturn(list);
        List<Post> postsEncontrados = assertDoesNotThrow(() -> service.findByTitle("Título da postagem"));

        assertNotNull(postsEncontrados);
        for (int i = 0; i < postsEncontrados.size(); i++) {
            assertEquals(list.get(i).getId(), postsEncontrados.get(i).getId());
            assertEquals(list.get(i).getDate(), postsEncontrados.get(i).getDate());
            assertEquals(list.get(i).getTitle(), postsEncontrados.get(i).getTitle());
            assertEquals(list.get(i).getBody(), postsEncontrados.get(i).getBody());
            assertEquals(list.get(i).getAuthor(), postsEncontrados.get(i).getAuthor());
        }
    }

    @Test
    void testFullSearch(){
        User user = new User("1", "Nome do autor", "email@email.com.br");
        AuthorDTO author = new AuthorDTO(user);
        Post post1 = new Post("11", new Date(), "Título da postagem 1", "String body 1", author);
        Post post2 = new Post("12", new Date(), "Título da postagem 2", "String body 2", author);

        List<Post> list = new ArrayList<>();
        list.add(post1);
        list.add(post2);

        when(repo.fullSearch(Mockito.anyString(), Mockito.any(), Mockito.any())).thenReturn(list);
        List<Post> postsEncontrados = assertDoesNotThrow(() -> service.fullSearch("Título", new Date(), new Date()));

        assertNotNull(postsEncontrados);
        for (int i = 0; i < postsEncontrados.size(); i++) {
            assertEquals(list.get(i).getId(), postsEncontrados.get(i).getId());
            assertEquals(list.get(i).getDate(), postsEncontrados.get(i).getDate());
            assertEquals(list.get(i).getTitle(), postsEncontrados.get(i).getTitle());
            assertEquals(list.get(i).getBody(), postsEncontrados.get(i).getBody());
            assertEquals(list.get(i).getAuthor(), postsEncontrados.get(i).getAuthor());
        }
    }

}