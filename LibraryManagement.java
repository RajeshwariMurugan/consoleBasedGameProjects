package src;

import java.util.*;

class Book {
    String title;
    String author;
    String isbn;
    boolean isAvailable;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
    }
}

public class LibraryManagement {
    static List<Book> books = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeBooks();
        
        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    viewBooks();
                    break;
                case 3:
                    borrowBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void initializeBooks() {
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "9780743273565"));
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", "9780061120084"));
        books.add(new Book("1984", "George Orwell", "9780451524935"));
    }

    static void addBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        
        books.add(new Book(title, author, isbn));
        System.out.println("Book added successfully!");
    }

    static void viewBooks() {
        System.out.println("\nBook List:");
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            String status = b.isAvailable ? "Available" : "Borrowed";
            System.out.printf("%d. %s by %s (ISBN: %s) - %s\n", 
                i+1, b.title, b.author, b.isbn, status);
        }
    }

    static void borrowBook() {
        viewBooks();
        System.out.print("Enter book number to borrow: ");
        int bookNum = scanner.nextInt() - 1;
        
        if (bookNum < 0 || bookNum >= books.size()) {
            System.out.println("Invalid book number!");
        } else if (!books.get(bookNum).isAvailable) {
            System.out.println("Book is already borrowed!");
        } else {
            books.get(bookNum).isAvailable = false;
            System.out.println("Book borrowed successfully!");
        }
    }

    static void returnBook() {
        viewBooks();
        System.out.print("Enter book number to return: ");
        int bookNum = scanner.nextInt() - 1;
        
        if (bookNum < 0 || bookNum >= books.size()) {
            System.out.println("Invalid book number!");
        } else if (books.get(bookNum).isAvailable) {
            System.out.println("Book is already in the library!");
        } else {
            books.get(bookNum).isAvailable = true;
            System.out.println("Book returned successfully!");
        }
    }
}