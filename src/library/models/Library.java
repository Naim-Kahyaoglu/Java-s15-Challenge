package library.models;

import java.util.*;

public class Library {
    private final Map<String, Book> books;          // Store books with their ID as the key
    private final Map<String, User> users;          // Store users with their ID as the key
    private final Map<String, Category> categories; // Store categories with their names as keys

    // Constructor
    public Library() {
        this.books = new HashMap<>();
        this.users = new HashMap<>();
        this.categories = new HashMap<>();
    }

    // Add a book to the library
    public void addBook(Book book) {
        if (books.containsKey(book.getId())) {
            System.out.println("Book with ID " + book.getId() + " already exists.");
            return;
        }
        books.put(book.getId(), book);
        categories.putIfAbsent(book.getCategory(), new Category(book.getCategory()));
        categories.get(book.getCategory()).addBook(book);
        System.out.println("Book '" + book.getTitle() + "' added successfully.");
    }

    // Remove a book from the library
    public void removeBook(String bookId) {
        Book book = books.get(bookId);
        if (book == null) {
            System.out.println("No book found with ID: " + bookId);
            return;
        }
        if (categories.containsKey(book.getCategory())) {
            categories.get(book.getCategory()).removeBook(book);
        }
        books.remove(bookId);
        System.out.println("Book '" + book.getTitle() + "' removed successfully.");
    }

    // Add a user to the library
    public void addUser(User user) {
        if (users.containsKey(user.getId())) {
            System.out.println("User with ID " + user.getId() + " already exists.");
            return;
        }
        users.put(user.getId(), user);
        System.out.println("User '" + user.getName() + "' added successfully.");
    }

    // Borrow a book for a user
    public void borrowBook(String userId, String bookId) {
        User user = users.get(userId);
        Book book = books.get(bookId);

        if (user == null) {
            System.out.println("No user found with ID: " + userId);
            return;
        }

        if (book == null) {
            System.out.println("No book found with ID: " + bookId);
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("Book '" + book.getTitle() + "' is currently not available.");
            return;
        }

        if (!user.canBorrow()) {
            System.out.println("User '" + user.getName() + "' has reached their borrowing limit.");
            return;
        }

        user.borrowBook(book);
        book.setAvailable(false);
        System.out.println("Book '" + book.getTitle() + "' borrowed by user '" + user.getName() + "'.");
    }

    // Return a book from a user
    public void returnBook(String userId, String bookId) {
        User user = users.get(userId);
        Book book = books.get(bookId);

        if (user == null) {
            System.out.println("No user found with ID: " + userId);
            return;
        }

        if (book == null) {
            System.out.println("No book found with ID: " + bookId);
            return;
        }

        if (!user.getBorrowedBooks().contains(book)) {
            System.out.println("This book was not borrowed by user '" + user.getName() + "'.");
            return;
        }

        user.returnBook(book);
        book.setAvailable(true);
        System.out.println("Book '" + book.getTitle() + "' returned by user '" + user.getName() + "'.");
    }

    // List books by category
    public void listBooksByCategory(String categoryName) {
        Category category = categories.get(categoryName);
        if (category == null) {
            System.out.println("No category found with name: " + categoryName);
            return;
        }
        System.out.println("Books in category '" + categoryName + "':");
        category.listBooks();
    }

    // List books by author
    public void listBooksByAuthor(String authorName) {
        System.out.println("Books by '" + authorName + "':");
        boolean found = false;
        for (Book book : books.values()) {
            if (book.getAuthor().equalsIgnoreCase(authorName)) {
                System.out.println("  - " + book.getTitle() + " (ID: " + book.getId() + ")");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books found by the author '" + authorName + "'.");
        }
    }

    // List all books
    public void listAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
        } else {
            System.out.println("All books in the library:");
            for (Book book : books.values()) {
                System.out.println("  - " + book.getTitle() + " by " + book.getAuthor() + " (ID: " + book.getId() + ")");
            }
        }
    }

    // Getter for books
    public Map<String, Book> getBooks() {
        return books;
    }

    // Getter for users
    public Map<String, User> getUsers() {
        return users;
    }

    // List all categories
    public void listAllCategories() {
        if (categories.isEmpty()) {
            System.out.println("No categories found in the library.");
        } else {
            System.out.println("Categories:");
            for (String categoryName : categories.keySet()) {
                System.out.println("  - " + categoryName);
            }
        }
    }

    // Get books in a specific category
    public List<Book> getBooksByCategory(String categoryName) {
        Category category = categories.get(categoryName);
        return category != null ? category.getBooks() : Collections.emptyList();
    }
}