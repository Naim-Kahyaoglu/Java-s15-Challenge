package library.models;

public class Book {
    // Fields
    private String id;            // Unique ID for the book
    private String title;         // Title of the book
    private String author;        // Author of the book
    private String category;      // Category/genre of the book
    private boolean isAvailable;  // Availability status
    private String borrowedBy;    // The ID of the user who borrowed the book (if any)

    // Constructor
    public Book(String id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isAvailable = true;         // Default to available when a new book is created
        this.borrowedBy = null;          // No borrower initially
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
        if (available) {
            this.borrowedBy = null; // Reset borrower when book becomes available
        }
    }

    public String getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(String borrowedBy) {
        this.borrowedBy = borrowedBy;
        // Set availability automatically when assigning borrower
        this.isAvailable = (borrowedBy == null);
    }

    /**
     * Method to get the display-friendly status of the book.
     * - If available, returns "can be borrowed".
     * - If not available, returns "borrowed by [user ID]".
     */
    public String getStatus() {
        return isAvailable ? "can be borrowed" : "borrowed by " + borrowedBy;
    }

    // toString method for displaying book details
    @Override
    public String toString() {
        return "ID: " + id +
                ", Title: " + title +
                ", Author: " + author +
                ", Category: " + category +
                ", Status: " + getStatus();
    }
}