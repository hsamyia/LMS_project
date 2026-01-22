package com.company.models;

public class User {
    private int id;
    private String name;
    private String surname;
    private boolean gender;
    private int level;

    public User(String name, String surname, boolean gender) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
    }

    public User(int id, String name, String surname, boolean gender, int level) {
        this(name, surname, gender);
        this.id = id;
        this.level = level;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public boolean getGender() {
        return gender;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender=" + (gender ? "Male" : "Female") +
                ", level=" + level +
                '}';
    }
}