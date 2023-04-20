package com.vanderleik.workshopmongo.resources;

import com.vanderleik.workshopmongo.domain.Post;
import com.vanderleik.workshopmongo.services.PostService;
import com.vanderleik.workshopmongo.utils.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

    @Autowired
    private PostService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Post> findById(@PathVariable String id) {
        Post obj = service.findById(id);

        return ResponseEntity.ok().body(obj);
    }

    //Exemplo de uso:
    //http://localhoast:8080/posts/titlesearch?text=bom%20dia
    @RequestMapping(value = "/titlesearch", method = RequestMethod.GET)
    public ResponseEntity<List<Post>> findByTitle(@RequestParam(value = "text", defaultValue = "") String text) {
        text = URL.decodeParam(text);
        List<Post> list = service.findByTitle(text);

        return ResponseEntity.ok().body(list);
    }

}
