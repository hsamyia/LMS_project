package com.company.repositories.interfaces;

import com.company.models.Course;
import java.util.List;

public interface ICourseRepository {
    boolean createCourse(Course course);
    Course getCourse(int id);
    List<Course> getAllCourses();
    boolean updateEnrolled(int id, int newValue);
    boolean enrollStudentInCourse(int userId, int courseId);
    List<Course> getCoursesByDifficulty(int difficulty);
}
