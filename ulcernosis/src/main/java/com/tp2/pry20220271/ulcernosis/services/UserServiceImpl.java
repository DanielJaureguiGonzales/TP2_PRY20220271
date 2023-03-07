package com.tp2.pry20220271.ulcernosis.services;

import com.tp2.pry20220271.ulcernosis.exceptions.NotFoundException;
import com.tp2.pry20220271.ulcernosis.models.entities.User;
import com.tp2.pry20220271.ulcernosis.models.repositories.UserRepository;
import com.tp2.pry20220271.ulcernosis.models.services.UserService;
import com.tp2.pry20220271.ulcernosis.resources.response.UserResource;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final ModelMapper modelMapper = new ModelMapper();
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<UserResource> findAllUsers() {
        List<User> users = repository.findAll();
        return users.stream().map(user -> modelMapper.map(user, UserResource.class)).toList();
    }

    @Override
    public UserResource findById(Long id) {
        User user = repository.findById(id).orElseThrow(()-> new NotFoundException("User","id",id));
        return modelMapper.map(user, UserResource.class);
    }
}
