package com.vanderleik.workshopmongo.services;

import com.vanderleik.workshopmongo.domain.Post;
import com.vanderleik.workshopmongo.repository.PostRepository;
import com.vanderleik.workshopmongo.services.exception.ObjectNotFoundException;
import com.vanderleik.workshopmongo.utils.TranslationsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository repo;

    public List<Post> findAll(){
        return repo.findAll();
    }

    public Post findById(String id) {
        var obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(TranslationsConstants.OBJETO_NAO_ENCONTRADO));
    }

}
