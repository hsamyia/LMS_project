package com.company;

import com.company.controllers.CourseController;
import com.company.controllers.interfaces.IUserController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MyApplication {
    private final Scanner scanner = new Scanner(System.in);

    private final IUserController userController;
    private final CourseController courseController;

    public MyApplication(IUserController userController, CourseController courseController) {
        this.userController = userController;
        this.courseController = courseController;
    }

    private void mainMenu() {
        System.out.println("\n=== My Application ===");
        System.out.println("1. Get all users");
        System.out.println("2. Get user by id");
        System.out.println("3. Create user");
        System.out.println("4. Enroll student in course");
        System.out.println("5. Edit user");
        System.out.println("6. Recommend courses for user");
        System.out.println("7. Create a new course");
        System.out.println("8. Show all courses");
        System.out.println("0. Exit");
        System.out.print("Enter option(0-8): ");
    }

    public void start() {
        while (true) {
            mainMenu();
            try {
                int option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1: getAllUsersMenu(); break;
                    case 2: getUserByIdMenu(); break;
                    case 3: createUserMenu(); break;
                    case 4: enrollMenu(); break;
                    case 5: editUserMenu(); break;
                    case 6: recommendCourseMenu(); break;
                    case 7: createCourseMenu(); break;
                    case 8: showAllCoursesMenu(); break;
                    case 0: return;
                    default: System.out.println("Invalid option"); break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Input must be integer");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("*************************");
        }
    }

    private void getAllUsersMenu() {
        String response = userController.getAllUsers();
        System.out.println(response);
    }

    private void getUserByIdMenu() {
        System.out.print("Enter user id: ");
        int id = Integer.parseInt(scanner.nextLine());
        String response = userController.getUser(id);
        System.out.println(response);
    }

    private void createUserMenu() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter gender (male/female): ");
        String gender = scanner.nextLine();
        System.out.print("Enter level (1-beginner, 2-intermediate, 3-advanced): ");
        int level = Integer.parseInt(scanner.nextLine());
        String response = userController.createUser(name, surname, gender, level);
        System.out.println(response);
    }

    private void enrollMenu() {
        System.out.print("Enter user id: ");
        int userId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter course id to enroll: ");
        int courseId = Integer.parseInt(scanner.nextLine());

        String response = courseController.enrollStudent(userId, courseId);
        System.out.println(response);
    }

    private void editUserMenu() {
        System.out.print("Enter user id to edit: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter new gender (male/female): ");
        String gender = scanner.nextLine();

        String response = userController.updateUser(id, name, surname, gender);
        System.out.println(response);
    }

    private void recommendCourseMenu(){
        System.out.print("Enter user id: ");
        int userId = Integer.parseInt(scanner.nextLine());
        String response = courseController.recommendCoursesForUser(userId);
        System.out.println(response);
    }

    private void createCourseMenu() {
        System.out.print("Enter course title: ");
        String title = scanner.nextLine();
        System.out.print("Enter course capacity: ");
        int capacity = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter course difficulty (1-5): ");
        int difficulty = Integer.parseInt(scanner.nextLine());

        String response = courseController.createCourse(title, capacity, difficulty);
        System.out.println(response);
    }

    private void showAllCoursesMenu() {
        String response = courseController.getAllCourses();
        System.out.println(response);
    }




}
