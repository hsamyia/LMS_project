package com.company.repositories.interfaces;
import com.company.models.User;
import java.util.List;

public interface IUserRepository {
    boolean createUser(User user);
    User getUser(int id);
    List<User> getAllUsers();
    boolean updateUser(int id, String name, String surname, boolean gender);
    int getUserLevel(int userId);
}