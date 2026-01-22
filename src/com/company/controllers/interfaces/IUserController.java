package com.company.controllers.interfaces;

public interface IUserController {
    String createUser(String name, String surname, String gender, int level);
    String getUser(int id);
    String getAllUsers();
    String updateUser(int id, String name, String surname, String gender);
}


