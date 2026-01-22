package com.company.controllers;

import com.company.models.Course;
import com.company.repositories.interfaces.ICourseRepository;
import com.company.repositories.interfaces.IUserRepository;

import java.util.List;

public class CourseController {
    private final ICourseRepository repo;
    private final IUserRepository userRepo;

    public CourseController(ICourseRepository repo, IUserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    public String createCourse(String title, int capacity, int difficulty) {
        Course course = new Course(title, capacity, 0);
        course.setDifficulty(difficulty);
        boolean created = repo.createCourse(course);
        return created ? "Course is created!" : "Failed to create course.";
    }

    public String getAllCourses() {
        List<Course> courses = repo.getAllCourses();
        if (courses.isEmpty()) return "No courses found.";
        StringBuilder sb = new StringBuilder("All courses:\n");
        for (Course c : courses) {
            sb.append("ID: ").append(c.getId())
                    .append(", Title: ").append(c.getTitle())
                    .append(", Capacity: ").append(c.getCapacity())
                    .append(", Enrolled: ").append(c.getEnrolled())
                    .append(", Difficulty: ").append(c.getDifficulty())
                    .append("\n");
        }
        return sb.toString();
    }

    public String enrollStudent(int userId, int courseId) {
        Course course = repo.getCourse(courseId);
        if (course == null) return "Course not found!";
        if (course.getEnrolled() >= course.getCapacity()) return "Course is full!";
        boolean enrolled = repo.enrollStudentInCourse(userId, courseId);
        if (!enrolled) return "User already enrolled in this course!";
        repo.updateEnrolled(courseId, course.getEnrolled() + 1);
        return "Student enrolled successfully!";
    }

    public String recommendCoursesForUser(int userId) {
        int level = userRepo.getUserLevel(userId);
        if (level == -1) return "User not found or level not set.";

        List<Course> courses = repo.getCoursesByDifficulty(level);
        if (courses.isEmpty()) return "No suitable courses found.";

        StringBuilder response = new StringBuilder();
        response.append("Recommended courses for user #").append(userId)
                .append(" (Level: ").append(level).append("):\n");

        for (Course course : courses) {
            response.append("Course ID: ").append(course.getId())
                    .append(", Title: ").append(course.getTitle())
                    .append(", Capacity: ").append(course.getCapacity())
                    .append(", Enrolled: ").append(course.getEnrolled())
                    .append("\n");
        }
        return response.toString();
    }
}



