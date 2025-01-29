package library.services;

import library.models.Book;
import library.models.Library;
import library.models.User;
import library.utils.InputValidator;

public class LibraryService {
    private final Library library;

    public LibraryService(Library library) {
        this.library = library;
    }

    public Library getLibrary() {
        return library;
    }

    public void addBook(String id, String title, String author, String category) {
        if (!InputValidator.isValidId(id) || !InputValidator.isValidString(title) ||
                !InputValidator.isValidString(author) || !InputValidator.isValidCategory(category)) {
            return; // Abort if invalid input
        }

        Book book = new Book(id, title, author, category);
        library.addBook(book);
    }

    public void addUser(String id, String name) {
        if (!InputValidator.isValidId(id) || !InputValidator.isValidString(name)) {
            return; // Abort if invalid input
        }

        User user = new User(id, name);
        library.addUser(user);
    }
    // Add a book silently (no log output)
    public void addBookSilently(String id, String title, String author, String category) {
        if (!InputValidator.isValidId(id) || !InputValidator.isValidString(title) ||
                !InputValidator.isValidString(author) || !InputValidator.isValidCategory(category)) {
            return; // Abort if invalid input
        }

        Book book = new Book(id, title, author, category);
        library.addBook(book, true); // Silent mode
    }

    // Add a user silently (no log output)
    public void addUserSilently(String id, String name) {
        if (!InputValidator.isValidId(id) || !InputValidator.isValidString(name)) {
            return; // Abort if invalid input
        }

        User user = new User(id, name);
        library.addUser(user, true); // Silent mode
    }
    public void borrowBook(String userId, String bookId) {
        // Ensure IDs are valid
        if (!InputValidator.isValidId(userId) || !InputValidator.isValidId(bookId)) {
            return;
        }

        // Find User and Book
        User user = library.getUsers().get(userId);
        Book book = library.getBooks().get(bookId);

        if (user == null) {
            System.out.println("No user found with ID: " + userId);
            return;
        }

        if (book == null) {
            System.out.println("No book found with ID: " + bookId);
            return;
        }

        // Validate borrowing rules
        if (!InputValidator.isWithinBorrowLimit(user) || !InputValidator.isBookAvailable(book)) {
            return;
        }

        // Borrow book
        library.borrowBook(userId, bookId);
    }
}