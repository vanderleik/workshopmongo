package com.vanderleik.workshopmongo.services;

import com.vanderleik.workshopmongo.domain.User;
import com.vanderleik.workshopmongo.dto.UserDTO;
import com.vanderleik.workshopmongo.repository.UserRepository;
import com.vanderleik.workshopmongo.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public List<User> findAll(){
        return repo.findAll();
    }

    public User findById(String id) {
        Optional<User> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public User insert(User obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        return repo.insert(obj);
    }

    public User fromDTO(UserDTO objDto) {
        if (Objects.isNull(objDto)){
            return null;
        }
        return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
    }
}
