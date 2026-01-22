package com.company.controllers;

import com.company.models.User;
import com.company.controllers.interfaces.IUserController;
import com.company.repositories.interfaces.IUserRepository;

import java.util.List;

public class UserController implements IUserController {
    private final IUserRepository repo;

    public UserController(IUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public String createUser(String name, String surname, String gender, int level) {
        boolean male = gender.equalsIgnoreCase("male");
        User user = new User(name, surname, male);
        user.setLevel(level);
        boolean created = repo.createUser(user);
        return created ? "User created!" : "Failed to create user!";
    }

    @Override
    public String getUser(int id) {
        User user = repo.getUser(id);
        return user == null ? "User not found!" : user.toString();
    }


    @Override
    public String getAllUsers() {
        List<User> users = repo.getAllUsers();
        StringBuilder sb = new StringBuilder();
        for (User user : users) {
            sb.append(user).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String updateUser(int id, String name, String surname, String gender) {
        boolean male = gender.equalsIgnoreCase("male");
        boolean updated = repo.updateUser(id, name, surname, male);
        return updated ? "User updated successfully!" : "Update failed!";
    }
}