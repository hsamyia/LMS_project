package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.User;
import com.company.repositories.interfaces.IUserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {
    private final IDB db;

    public UserRepository(IDB db) { this.db = db; }

    @Override
    public boolean createUser(User user) {
        try (Connection con = db.getConnection()) {
            String sql = "INSERT INTO users(name,surname,gender,level) VALUES (?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, user.getName());
            st.setString(2, user.getSurname());
            st.setBoolean(3, user.getGender());
            st.setInt(4, user.getLevel());
            st.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public User getUser(int id) {
        try (Connection con = db.getConnection()) {
            String sql = "SELECT id,name,surname,gender,level FROM users WHERE id=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getBoolean("gender"),
                        rs.getInt("level"));
            }
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection con = db.getConnection()) {
            String sql = "SELECT id,name,surname,gender,level FROM users";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                users.add(new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getBoolean("gender"),
                        rs.getInt("level")));
            }
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }
        return users;
    }

    @Override
    public boolean updateUser(int id, String name, String surname, boolean gender) {
        try (Connection con = db.getConnection()) {
            String sql = "UPDATE users SET name=?, surname=?, gender=? WHERE id=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, surname);
            st.setBoolean(3, gender);
            st.setInt(4, id);
            int rows = st.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
            return false;
        }
    }
    @Override
    public int getUserLevel(int userId) {
        try (Connection con = db.getConnection()) {
            String sql = "SELECT level FROM users WHERE id=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) return rs.getInt("level");
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }
        return -1;
    }
}



