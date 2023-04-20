package com.vanderleik.workshopmongo.repository;

import com.vanderleik.workshopmongo.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
}
