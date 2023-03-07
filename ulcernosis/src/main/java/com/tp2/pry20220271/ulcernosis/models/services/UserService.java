package com.tp2.pry20220271.ulcernosis.models.services;


import com.tp2.pry20220271.ulcernosis.resources.response.UserResource;

import java.util.List;

public interface UserService {
    List<UserResource> findAllUsers();
    UserResource findById(Long id);
}
