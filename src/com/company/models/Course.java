package com.company.models;

public class Course {
    private int id;
    private String title;
    private int capacity;
    private int enrolled;
    private int difficulty;

    public Course() {}

    public Course(String title, int capacity, int enrolled) {
        this.title = title;
        this.capacity = capacity;
        this.enrolled = enrolled;
    }

    public Course(int id, String title, int capacity, int enrolled, int difficulty) {
        this(title, capacity, enrolled);
        this.id = id;
        this.difficulty = difficulty;
    }

    public int getId() { return id; }
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
}
