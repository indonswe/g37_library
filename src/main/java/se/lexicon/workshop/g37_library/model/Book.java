package se.lexicon.workshop.g37_library.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Entity // Making a table
public class Book {

    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Strategy for generating Id.
    private int bookId;
    private String isbn;
    private String title;
    private int maxLoanDays;

    @ManyToMany (cascade = {DETACH, MERGE, REFRESH, PERSIST},
            fetch = FetchType.LAZY,
    mappedBy = "writtenBooks")

    private Set<Author> authors;

    public Book() {
    }

    public Book(String isbn, String title, int maxLoanDays) {
        this.isbn = isbn;
        this.title = title;
        this.maxLoanDays = maxLoanDays;
    }

    public Book(int bookId, String isbn, String title, int maxLoanDays, Set<Author> authors) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.maxLoanDays = maxLoanDays;
        this.authors = authors;
    }

    public void addAuthor(Author author){

        if(author == null) throw new IllegalArgumentException("Author can not be null!");
        if (authors == null) authors = new HashSet<>();

        if (!authors.contains(author)){
            authors.add(author);
            author.getWrittenBooks().add(this);
        }
    }

    public void removeAuthor(Author author){

        if(author == null) throw new IllegalArgumentException("Author can not be null!");
        if (authors == null) authors = new HashSet<>();

        if (authors.contains(author)){
            authors.remove(author);
            author.getWrittenBooks().remove(this);
        }
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMaxLoanDays() {
        return maxLoanDays;
    }

    public void setMaxLoanDays(int maxLoanDays) {
        this.maxLoanDays = maxLoanDays;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return getMaxLoanDays() == book.getMaxLoanDays() && Objects.equals(getIsbn(), book.getIsbn()) && Objects.equals(getTitle(), book.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIsbn(), getTitle(), getMaxLoanDays());
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", maxLoanDays=" + maxLoanDays +
                '}';
    }
}
