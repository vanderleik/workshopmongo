package com.vanderleik.workshopmongo.services;

import com.vanderleik.workshopmongo.domain.User;
import com.vanderleik.workshopmongo.dto.UserDTO;
import com.vanderleik.workshopmongo.repository.UserRepository;
import com.vanderleik.workshopmongo.services.exception.ObjectNotFoundException;
import com.vanderleik.workshopmongo.utils.TranslationsConstants;
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
        var obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(TranslationsConstants.OBJETO_NAO_ENCONTRADO));
    }

    public User insert(User obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        return repo.insert(obj);
    }

    public void delete(String id) {
        findById(id);
        repo.deleteById(id);
    }

    public User update(User obj) {
        Optional<User> newObj = repo.findById(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj.get());
    }

    private void updateData(Optional<User> newObj, User obj) {
        newObj.get().setName(obj.getName());
        newObj.get().setEmail(obj.getEmail());
    }

    public User fromDTO(UserDTO objDto) {
        if (Objects.isNull(objDto)){
            return null;
        }
        return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
    }
}
