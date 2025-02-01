package library.models;

import java.time.LocalDate;

public class Transaction {
    private String userId;      // ID of the user involved in the transaction
    private String bookId;      // ID of the borrowed/returned book
    private LocalDate borrowDate; // Date when the book was borrowed
    private LocalDate returnDate; // Date when the book was returned (null if not returned yet)
    private double cost;          // Cost of borrowing the book

    public Transaction(String userId, String bookId, LocalDate borrowDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = null; // Not returned yet
        this.cost = 0.0;        // Default cost, calculated on return
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public String getBookId() {
        return bookId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}