package com.company.models;

public class AuthUser {
    private int id;
    private String username;
    private String role;
    private boolean blocked;

    public AuthUser(int id, String username, String role, boolean blocked) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.blocked = blocked;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public boolean isBlocked() {
        return blocked;
    }
}

