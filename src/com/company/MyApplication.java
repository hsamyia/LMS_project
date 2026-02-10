package com.company;

import com.company.controllers.CourseController;
import com.company.controllers.interfaces.IUserController;
import com.company.models.AuthUser;
import com.company.services.AuthService;


import java.util.Scanner;

public class MyApplication {
    private final Scanner scanner = new Scanner(System.in);

    private final IUserController userController;
    private final CourseController courseController;
    private AuthUser currentUser;
    private final AuthService authService;

    public MyApplication(IUserController userController, CourseController courseController, AuthService authService) {
        this.userController = userController;
        this.courseController = courseController;
        this.authService = authService;
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
        if (currentUser.getRole().equals("ADMIN")) {
            System.out.println("9. Block / Unblock user");
        }
        System.out.println("10. Show user's enrolled courses");
        System.out.println("11. Show courses by category");
        System.out.println("0. Exit");
        System.out.print("Enter option(0-11): ");
    }

    public void start() {
        loginMenu();
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
                    case 9:
                        if (currentUser.getRole().equals("ADMIN")) {
                            blockUserMenu();
                        } else {
                            System.out.println("Access denied");
                        }
                        break;
                    case 10: showUserCoursesMenu(); break;
                    case 11: showCoursesByCategoryMenu(); break;
                    case 0: return;
                    default: System.out.println("Invalid option"); break;
                }

            }
            catch (NumberFormatException e) {
                System.out.println("Input must be integer");
            }
            catch (Exception e) {
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
        String title = readNonEmptyString("Enter course title:  ");
        int capacity = readInt("Enter course capacity (>0): ", 1, 1000);

        int difficulty = readInt("Enter course difficulty (1-3): ", 1, 3);
        String category = readNonEmptyString("Enter course category: ");

        String response = courseController.createCourse(title, capacity, difficulty, category);
        System.out.println(response);
    }

    private void showAllCoursesMenu() {
        String response = courseController.getAllCourses();
        System.out.println(response);
    }

    private void loginMenu() {
        while (currentUser == null) {
            int choice;
            while (true) {
                System.out.println("1. Login");
                System.out.println("2. Sign up");
                System.out.print("Enter option (1-2): ");

                String input = scanner.nextLine();
                try {
                    choice = Integer.parseInt(input);
                    if (choice == 1 || choice == 2) {
                        break;
                    } else {
                        System.out.println("Please enter ONLY 1 or 2.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input — enter a NUMBER (1 or 2).");
                }
            }
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (choice == 1) {
                currentUser = authService.login(username, password);
                if (currentUser == null) {
                    System.out.println("*** Wrong username or password ***");
                }
                else if (currentUser.isBlocked()) {
                    System.out.println("*** You are blocked ***");
                    currentUser = null;
                }
            }

            else {
                int userId;
                while (true) {
                    System.out.print("Enter your existing user id: ");
                    try {
                        userId = Integer.parseInt(scanner.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("User id must be a NUMBER!");
                    }
                }
                boolean success = authService.signup(userId, username, password);
                System.out.println(
                        success
                                ? "*** Signup completed successfully ***"
                                : "*** Signup failed (check user id or username) ***"
                );
            }
        }
    }

    private void blockUserMenu() {
        System.out.print("Enter user id: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("*** Block user? (true / false): ");
        boolean blocked = Boolean.parseBoolean(scanner.nextLine());

        String response = userController.blockUser(id, blocked);
        System.out.println(response);
    }

    private void showUserCoursesMenu() {
        System.out.print("Enter user id: ");
        int userId = Integer.parseInt(scanner.nextLine());

        String response = courseController.getUserCourses(userId);
        System.out.println(response);
    }

    private int readInt(String prompt, int min, int max) {
        int value;
        while (true) {
            try {
                System.out.print(prompt);
                value = Integer.parseInt(scanner.nextLine());
                if (value < min || value > max) {
                    System.out.println("Value must be between " + min + " and " + max);
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number!");
            }
        }
    }

    private String readNonEmptyString(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
        } while (input.isEmpty());
        return input;
    }
    private void showCoursesByCategoryMenu() {
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        String response = courseController.getCoursesByCategory(category);
        System.out.println(response);
    }



}