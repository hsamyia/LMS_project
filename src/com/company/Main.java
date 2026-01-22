package com.company;

import com.company.controllers.CourseController;
import com.company.controllers.UserController;
import com.company.controllers.interfaces.IUserController;
import com.company.data.PostgresDB;
import com.company.data.interfaces.IDB;
import com.company.repositories.CourseRepository;
import com.company.repositories.UserRepository;
import com.company.repositories.interfaces.ICourseRepository;
import com.company.repositories.interfaces.IUserRepository;

public class Main {
    public static void main(String[] args) {
        IDB db = new PostgresDB("jdbc:postgresql://localhost:5432", "postgres", "0000", "postgres");

        IUserRepository userRepo = new UserRepository(db);
        IUserController userController = new UserController(userRepo);

        ICourseRepository courseRepo = new CourseRepository(db);
        CourseController courseController = new CourseController(courseRepo, userRepo);

        MyApplication app = new MyApplication(userController, courseController);

        app.start();

        db.close();
    }
}
