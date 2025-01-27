package library.utils;

import library.models.Book;
import library.models.User;

public class InputValidator {

    // Validate that a string is not null or empty
    public static boolean isValidString(String value) {
        if (value == null || value.trim().isEmpty()) {
            System.out.println("Invalid input: String cannot be null or empty.");
            return false;
        }
        return true;
    }

    // Validate that an ID is valid (non-null, non-empty, matches a pattern)
    public static boolean isValidId(String id) {
        if (!isValidString(id)) {
            System.out.println("Invalid input: ID cannot be null or empty.");
            return false;
        }
        if (!id.matches("^[A-Za-z0-9]{3,}$")) { // Allows alphanumeric IDs with at least 3 characters
            System.out.println("Invalid input: ID must be alphanumeric and at least 3 characters long.");
            return false;
        }
        return true;
    }

    // Validate if a user can borrow more books
    public static boolean isWithinBorrowLimit(User user) {
        if (!user.canBorrow()) {
            System.out.println("User '" + user.getName() + "' has reached the maximum borrowing limit.");
            return false;
        }
        return true;
    }

    // Validate if a book is available for borrowing
    public static boolean isBookAvailable(Book book) {
        if (!book.isAvailable()) {
            System.out.println("The book '" + book.getTitle() + "' is currently not available.");
            return false;
        }
        return true;
    }

    // Validate if a category name is valid
    public static boolean isValidCategory(String category) {
        if (!isValidString(category)) {
            System.out.println("Invalid input: Category name cannot be null or empty.");
            return false;
        }
        return true;
    }
}