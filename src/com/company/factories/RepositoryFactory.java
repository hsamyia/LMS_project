package com.company.factories;

import com.company.data.PostgresDB;
import com.company.data.interfaces.IDB;
import com.company.repositories.CourseRepository;
import com.company.repositories.UserRepository;
import com.company.repositories.interfaces.ICourseRepository;
import com.company.repositories.interfaces.IUserRepository;

public final class RepositoryFactory {
    private static final IDB db = PostgresDB.getInstance();

    private RepositoryFactory() {

    }

    public static IUserRepository getUserRepository() {
        return new UserRepository(db);
    }

    public static ICourseRepository getCourseRepository() {
        return new CourseRepository(db);
    }
}