package library.models;

import java.util.*;
import java.time.LocalDate;

public class Library {
    private final Map<String, Book> books;          // Store books with their ID as the key
    private final Map<String, User> users;          // Store users with their ID as the key
    private final Map<String, Category> categories; // Store categories with their names as keys
    private final Map<String, List<Transaction>> transactions; // Key: User ID, Value: List of transactions

    // Update the constructor to initialize the transactions map
    public Library() {
        this.books = new HashMap<>();
        this.users = new HashMap<>();
        this.categories = new HashMap<>();
        this.transactions = new HashMap<>();
    }

    // Add a book to the library
    public void addBook(Book book, boolean silent) {
        if (books.containsKey(book.getId())) {
            if (!silent) System.out.println("Book with ID " + book.getId() + " already exists.");
            return;
        }
        books.put(book.getId(), book);
        categories.putIfAbsent(book.getCategory(), new Category(book.getCategory()));
        categories.get(book.getCategory()).addBook(book);
        if (!silent) System.out.println("Book '" + book.getTitle() + "' added successfully.");
    }

    // Overloaded method for backward compatibility (defaults to non-silent mode)
    public void addBook(Book book) {
        addBook(book, false);
    }

    // Add a user to the library
    public void addUser(User user, boolean silent) {
        if (users.containsKey(user.getId())) {
            if (!silent) System.out.println("User with ID " + user.getId() + " already exists.");
            return;
        }
        users.put(user.getId(), user);
        if (!silent) System.out.println("User '" + user.getName() + "' added successfully.");
    }

    // Overloaded method for backward compatibility (defaults to non-silent mode)
    public void addUser(User user) {
        addUser(user, false);
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

        // Borrow the book
        user.borrowBook(book);
        book.setBorrowedBy(userId);

        // Create a new transaction
        Transaction transaction = new Transaction(userId, bookId, LocalDate.now());
        transactions.putIfAbsent(userId, new ArrayList<>());
        transactions.get(userId).add(transaction);

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

        // Return the book
        user.returnBook(book);
        book.setAvailable(true);

        // Find the relevant transaction
        List<Transaction> userTransactions = transactions.get(userId);

        Transaction transaction = userTransactions.stream()
                .filter(t -> t.getBookId().equals(bookId) && t.getReturnDate() == null)
                .findFirst()
                .orElse(null);

        if (transaction == null) {
            System.out.println("No active transaction found for this book.");
            return;
        }

        // Update the transaction with return date
        transaction.setReturnDate(LocalDate.now());

        // Calculate the cost (e.g., $1.5 per day)
        long daysBorrowed = java.time.temporal.ChronoUnit.DAYS.between(transaction.getBorrowDate(), transaction.getReturnDate());
        double cost = daysBorrowed * 1.5;
        transaction.setCost(cost);

        System.out.println("Book '" + book.getTitle() + "' returned by user '" + user.getName() + "'.");
        System.out.println("Total borrowing cost: $" + cost);

        // Issue a refund (if applicable)
        System.out.println("A refund of $" + cost + " has been processed.");
    }
    // List books by category
    // List books in a specific category by name
    public void listBooksByCategory(String categoryName) {
        Category category = categories.get(categoryName);
        if (category == null) {
            System.out.println("Category '" + categoryName + "' does not exist.");
        } else {
            category.listBooks();
        }
    }
    // List all transactions of a user
    // In Library.java
    public void listUserTransactions(String userId) {
        // Fetch the user with the given ID
        User user = users.get(userId);

        if (user == null) {
            System.out.println("No user found with the given ID.");
            return;
        }

        // Fetch the transactions for this user
        List<Transaction> userTransactions = transactions.get(userId);

        if (userTransactions == null || userTransactions.isEmpty()) {
            System.out.println("No transactions found for user: " + user.getName());
            return;
        }

        // Display the user's transaction history
        System.out.println("Transaction history for user: " + user.getName());
        for (Transaction transaction : userTransactions) {
            System.out.println(transaction);
        }
    }
    // Get a set of all unique authors in the library
    public Set<String> getAllAuthors() {
        Set<String> authors = new HashSet<>();
        for (Book book : books.values()) {
            authors.add(book.getAuthor());
        }
        return authors;
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
// List all books in numbered order with proper availability status
    public void listAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
            return;
        }

        System.out.println("All books in the library:");
        int index = 1; // To display books in numbered order

        for (Book book : books.values()) {
            // Determine the availability status
            String availabilityStatus = book.isAvailable() ? "can be borrowed" : "borrowed by " + getBorrowerName(book);

            // Display book details in the required format
            System.out.println(index + ". " + book.getTitle() + " by " + book.getAuthor() +
                    " (Category: " + book.getCategory() + ", Status: " + availabilityStatus + ")");
            index++;
        }
    }

    // Helper method to find the name of the borrower for a specific book
    private String getBorrowerName(Book book) {
        // Iterate through the users to find who borrowed the book
        for (User user : users.values()) {
            if (user.getBorrowedBooks().contains(book)) {
                return user.getName(); // Return the borrower's name
            }
        }
        return "Unknown"; // Fallback in case no borrower is found (should not happen ideally)
    }

    // Get books in a specific category
    public List<Book> getBooksByCategory(String categoryName) {
        Category category = categories.get(categoryName);
        return category != null ? category.getBooks() : Collections.emptyList();
    }

    // Getter for categories (added for Main.java compatibility)
    public Map<String, Category> getCategories() {
        return categories;
    }

    // Getter for books
    public Map<String, Book> getBooks() {
        return books;
    }

    // Getter for users
    public Map<String, User> getUsers() {
        return users;
    }
}
