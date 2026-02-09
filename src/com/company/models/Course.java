package com.company.models;

public class Course {
    private int id;
    private String title;
    private int capacity;
    private int enrolled;
    private int difficulty;
    private String category;

    public Course(String title, int capacity, int enrolled) {
        this.title = title;
        this.capacity = capacity;
        this.enrolled = enrolled;
    }
    public Course(String title, int capacity, int enrolled, String category) {
        this.title = title;
        this.capacity = capacity;
        this.enrolled = enrolled;
        this.category = category;
    }

    public Course(int id, String title, int capacity, int enrolled, int difficulty, String category) {
        this(title, capacity, enrolled, category);
        this.id = id;
        this.difficulty = difficulty;
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public int getCapacity() {
        return capacity;
    }
    public int getEnrolled() {
        return enrolled;
    }
    public void setEnrolled(int enrolled) {
        this.enrolled = enrolled;
    }
    public int getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
}
