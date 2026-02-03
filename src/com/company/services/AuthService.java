package com.company.services;

import com.company.data.PostgresDB;
import com.company.data.interfaces.IDB;
import com.company.models.AuthUser;

import java.sql.*;

public class AuthService {
    private static AuthService instance;
    private final IDB db;

    private AuthService() {
        this.db = PostgresDB.getInstance();
    }

    public static AuthService getInstance() {
        if (instance == null) {
            synchronized (AuthService.class){
                if (instance == null){
                    instance = new AuthService();
                }
            }
        }
        return instance;
    }

    public AuthUser login(String username, String password) {
        try (Connection con = db.getConnection()) {
            String sql = "SELECT id, role, blocked FROM users WHERE username=? AND password=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new AuthUser(
                        rs.getInt("id"),
                        username,
                        rs.getString("role"),
                        rs.getBoolean("blocked")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean signup(int userId, String username, String password) {
        if (username.isBlank() || password.length() < 4) return false;

        try (Connection con = db.getConnection()) {


            String checkUser = "SELECT id FROM users WHERE id=?";
            PreparedStatement checkSt = con.prepareStatement(checkUser);
            checkSt.setInt(1, userId);
            ResultSet rs = checkSt.executeQuery();

            if (!rs.next()) return false;


            String sql = "UPDATE users SET username=?, password=? WHERE id=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            st.setInt(3, userId);

            return st.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        }
    }

}