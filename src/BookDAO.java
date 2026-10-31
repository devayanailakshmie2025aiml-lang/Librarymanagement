
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // 1. ADD BOOK

    public void addBook(Book book) throws SQLException {

        String checkSql = "SELECT 1 FROM books WHERE book_id = ?";
        String sql = "INSERT INTO books (book_id, title, author, quantity) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {
            try (PreparedStatement check = con.prepareStatement(checkSql)) {
                check.setInt(1, book.getBookId());
                try (ResultSet rs = check.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("ERROR: Book ID " + book.getBookId()
                                + " already exists. Please enter a different ID.");
                        return;
                    }
                }
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, book.getBookId());
                ps.setString(2, book.getTitle());
                ps.setString(3, book.getAuthor());
                ps.setInt(4, book.getQuantity());

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Book added successfully!");
                } else {
                    System.out.println("Book was not added!");
                }
            }
        } catch (SQLException e) {
            // Keep a clear message if another insert uses the ID between the check and insert.
            if (e.getErrorCode() == 1062 || "23000".equals(e.getSQLState())) {
                System.out.println("ERROR: Book ID " + book.getBookId()
                        + " already exists. Please enter a different ID.");
                return;
            }
            throw e;
        }
    }


    // 2. VIEW ALL BOOKS

    public List<Book> getAllBooks() throws SQLException {

        List<Book> books = new ArrayList<>();

        String sql = "SELECT book_id, title, author, quantity FROM books ORDER BY book_id";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                int id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int quantity = rs.getInt("quantity");

                Book book = new Book(id, title, author, quantity);

                books.add(book);
            }
        }

        System.out.println("Database retrieval completed.");
        System.out.println("Total books retrieved: " + books.size());

        return books;
    }


    // 3. SEARCH BOOK BY ID

    public Book getBookById(int id) throws SQLException {

        String sql = "SELECT book_id, title, author, quantity FROM books WHERE book_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Book book = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("quantity")
                    );

                    System.out.println("Book retrieved from database.");

                    return book;
                }
            }
        }

        return null;
    }


    // 4. UPDATE BOOK

    public boolean updateBook(Book book) throws SQLException {

        String sql = "UPDATE books SET title = ?, author = ?, quantity = ? WHERE book_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getQuantity());
            ps.setInt(4, book.getBookId());

            int rows = ps.executeUpdate();

            return rows > 0;
        }
    }


    // 5. DELETE BOOK

    public boolean deleteBook(int id) throws SQLException {

        String sql = "DELETE FROM books WHERE book_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            return rows > 0;
        }
    }
}
