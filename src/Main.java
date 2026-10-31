
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BookDAO dao = new BookDAO();

        // Check database connection

        try (Connection con = DBConnection.getConnection()) {

            System.out.println("Database connected successfully!");

        } catch (SQLException e) {

            System.out.println("Database connection failed!");
            e.printStackTrace();

            sc.close();
            return;
        }

        // Main application menu

        while (true) {

            System.out.println("\n====================================");
            System.out.println("       LIBRARY MANAGEMENT SYSTEM");
            System.out.println("====================================");

            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book");
            System.out.println("4. Update Book");
            System.out.println("5. Delete Book");
            System.out.println("6. Exit");

            System.out.println("====================================");

            int choice = readInt(sc, "Enter your choice: ");

            try {

                switch (choice) {

                    // OPTION 1: ADD BOOK

                    case 1:

                        System.out.println("\n--- ADD BOOK ---");

                        int bookId = readInt(sc, "Enter Book ID: ");
                        if (bookId <= 0) {
                            System.out.println("Book ID must be a positive number!");
                            break;
                        }
                        String title = readText(sc, "Enter Book Title: ");
                        String author = readText(sc, "Enter Author Name: ");
                        int quantity = readInt(sc, "Enter Quantity: ");

                        if (quantity < 0) {
                            System.out.println("Quantity cannot be negative!");
                            break;
                        }

                        Book newBook = new Book(bookId, title, author, quantity);

                        dao.addBook(newBook);

                        break;


                    // OPTION 2: VIEW ALL BOOKS

                    case 2:

                        System.out.println("\n--- VIEW ALL BOOKS ---");
                        System.out.println("Fetching books from database...");

                        List<Book> books = dao.getAllBooks();

                        if (books.isEmpty()) {

                            System.out.println("No books available in the library.");

                        } else {

                            System.out.println("\nBOOK DETAILS");

                            for (Book book : books) {

                                book.displayBook();
                            }
                        }

                        break;


                    // OPTION 3: SEARCH BOOK

                    case 3:

                        System.out.println("\n--- SEARCH BOOK ---");

                        int searchId = readInt(sc, "Enter Book ID: ");

                        System.out.println("Searching for book...");

                        Book searchedBook = dao.getBookById(searchId);

                        if (searchedBook != null) {

                            System.out.println("\nBOOK FOUND!");

                            searchedBook.displayBook();

                        } else {

                            System.out.println("Book not found with ID: " + searchId);
                        }

                        break;


                    // OPTION 4: UPDATE BOOK

                    case 4:

                        System.out.println("\n--- UPDATE BOOK ---");

                        int updateId = readInt(sc, "Enter Book ID to update: ");

                        Book existingBook = dao.getBookById(updateId);

                        if (existingBook == null) {

                            System.out.println("Book not found!");

                        } else {

                            System.out.println("\nCurrent Book Details:");
                            existingBook.displayBook();

                            System.out.println("Enter New Details:");

                            String newTitle = readText(sc, "Enter New Title: ");
                            String newAuthor = readText(sc, "Enter New Author: ");
                            int newQuantity = readInt(sc, "Enter New Quantity: ");

                            if (newQuantity < 0) {
                                System.out.println("Quantity cannot be negative!");
                                break;
                            }

                            Book updatedBook = new Book(
                                updateId,
                                newTitle,
                                newAuthor,
                                newQuantity
                            );

                            boolean result = dao.updateBook(updatedBook);

                            if (result) {
                                System.out.println("Book updated successfully!");
                            } else {
                                System.out.println("Book update failed!");
                            }
                        }

                        break;


                    // OPTION 5: DELETE BOOK

                    case 5:

                        System.out.println("\n--- DELETE BOOK ---");

                        int deleteId = readInt(sc, "Enter Book ID to delete: ");

                        boolean deleted = dao.deleteBook(deleteId);

                        if (deleted) {

                            System.out.println("Book deleted successfully!");

                        } else {

                            System.out.println("Book not found!");

                        }

                        break;


                    // OPTION 6: EXIT

                    case 6:

                        System.out.println("\nThank you for using Library Management System!");

                        sc.close();

                        return;


                    default:

                        System.out.println("Invalid choice! Enter a number from 1 to 6.");

                }

            } catch (SQLException e) {

                System.out.println("Database operation failed!");
                System.out.println("Error: " + e.getMessage());

                e.printStackTrace();
            }
        }
    }


    // METHOD TO READ INTEGER INPUT

    public static int readInt(Scanner sc, String message) {

        while (true) {

            System.out.print(message);

            try {

                return Integer.parseInt(sc.nextLine().trim());

            } catch (NumberFormatException e) {

                System.out.println("Invalid input. Please enter a whole number using digits only!");
            }
        }
    }


    // METHOD TO READ STRING INPUT

    public static String readText(Scanner sc, String message) {

        while (true) {

            System.out.print(message);

            String value = sc.nextLine().trim();

            if (!value.isEmpty()) {

                return value;

            } else {
         System.out.println("This field cannot be empty!");
            }
        }
    }
}
