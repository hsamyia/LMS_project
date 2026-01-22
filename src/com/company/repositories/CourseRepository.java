package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.Course;
import com.company.repositories.interfaces.ICourseRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository implements ICourseRepository {
    private final IDB db;

    public CourseRepository(IDB db) { this.db = db; }

    @Override
    public boolean createCourse(Course course) {
        try (Connection con = db.getConnection()) {
            String sql = "INSERT INTO courses(title, capacity, enrolled, difficulty) VALUES (?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, course.getTitle());
            st.setInt(2, course.getCapacity());
            st.setInt(3, course.getEnrolled());
            st.setInt(4, course.getDifficulty());
            st.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Course getCourse(int id) {
        try (Connection con = db.getConnection()) {
            String sql = "SELECT id,title,capacity,enrolled,difficulty FROM courses WHERE id=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Course(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("capacity"),
                        rs.getInt("enrolled"),
                        rs.getInt("difficulty"));
            }
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        try (Connection con = db.getConnection()) {
            String sql = "SELECT id,title,capacity,enrolled,difficulty FROM courses";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                courses.add(new Course(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("capacity"),
                        rs.getInt("enrolled"),
                        rs.getInt("difficulty")));
            }
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }
        return courses;
    }

    @Override
    public boolean updateEnrolled(int id, int newValue) {
        try (Connection con = db.getConnection()) {
            String sql = "UPDATE courses SET enrolled=? WHERE id=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, newValue);
            st.setInt(2, id);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean enrollStudentInCourse(int userId, int courseId) {
        try (Connection con = db.getConnection()) {
            String checkSql = "SELECT 1 FROM enrollment WHERE user_id=? AND course_id=?";
            PreparedStatement checkStmt = con.prepareStatement(checkSql);
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, courseId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) return false;

            String sql = "INSERT INTO enrollment(user_id, course_id) VALUES (?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, courseId);
            int rowsInserted = st.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Course> getCoursesByDifficulty(int difficulty) {
        List<Course> courses = new ArrayList<>();
        try (Connection con = db.getConnection()) {
            String sql = "SELECT id,title,capacity,enrolled,difficulty FROM courses WHERE difficulty=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, difficulty);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                courses.add(new Course(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("capacity"),
                        rs.getInt("enrolled"),
                        rs.getInt("difficulty")
                ));
            }
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }
        return courses;
    }
}