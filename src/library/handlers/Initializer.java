package library.handlers;

import library.models.Library;
import library.models.User;
import library.services.LibraryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Initializer {
    public static void initializeLibrary(LibraryService libraryService) {
        libraryService.addBookSilently("BK1", "The Catcher in the Rye", "J.D. Salinger", "Fiction");
        libraryService.addBookSilently("BK2", "To Kill a Mockingbird", "Harper Lee", "Fiction");
        libraryService.addBookSilently("BK3", "1984", "George Orwell", "Dystopian");
        libraryService.addBookSilently("BK4", "Moby Dick", "Herman Melville", "Adventure");
        libraryService.addBookSilently("BK5", "The Great Gatsby", "F. Scott Fitzgerald", "Fiction");
        libraryService.addBookSilently("BK6", "War and Peace", "Leo Tolstoy", "Historical");
        libraryService.addBookSilently("BK7", "Pride and Prejudice", "Jane Austen", "Romance");

        libraryService.addUserSilently("U01", "Alice");
        libraryService.addUserSilently("U02", "Bob");
        libraryService.addUserSilently("U03", "Charlie");
        libraryService.addUserSilently("U04", "Diana");

        System.out.println("Library initialized with predefined books and users.");
    }

    public static User selectUser(Library library, Scanner scanner) {
        List<User> userList = new ArrayList<>(library.getUsers().values());
        if (userList.isEmpty()) {
            System.out.println("No users found. Please contact the admin.");
            return null;
        }

        System.out.println("Select a user to proceed:");
        for (int i = 0; i < userList.size(); i++) {
            System.out.println((i + 1) + ". " + userList.get(i).getName() + " (ID: " + userList.get(i).getId() + ")");
        }

        System.out.print("Enter the number corresponding to your choice: ");
        try {
            int choice = scanner.nextInt();
            if (choice >= 1 && choice <= userList.size()) {
                return userList.get(choice - 1);
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Exiting...");
        }
        return null;
    }
}