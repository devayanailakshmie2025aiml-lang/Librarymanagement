
public class Book {

    private int bookId;
    private String title;
    private String author;
    private int quantity;

    // Constructor for a new book
    public Book(String title, String author, int quantity) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    // Constructor for an existing book
    public Book(int bookId, String title, String author, int quantity) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    // Getters

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Display book information

    public void displayBook() {

        System.out.println("--------------------------------");
        System.out.println("Book ID   : " + bookId);
        System.out.println("Title     : " + title);
        System.out.println("Author    : " + author);
        System.out.println("Quantity  : " + quantity);
        System.out.println("--------------------------------");
    }
}
