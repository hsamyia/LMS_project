package com.company;

import com.company.controllers.CourseController;
import com.company.controllers.UserController;
import com.company.controllers.interfaces.IUserController;
import com.company.factories.RepositoryFactory;
import com.company.repositories.interfaces.ICourseRepository;
import com.company.repositories.interfaces.IUserRepository;
import com.company.services.AuthService;

public class Main {
    public static void main(String[] args) {


        IUserRepository userRepo = RepositoryFactory.getUserRepository();
        IUserController userController = new UserController(userRepo);

        ICourseRepository courseRepo = RepositoryFactory.getCourseRepository();
        CourseController courseController = new CourseController(courseRepo, userRepo);

        AuthService authService = AuthService.getInstance();
        MyApplication app = new MyApplication(userController, courseController, authService);

        app.start();

    }
}


