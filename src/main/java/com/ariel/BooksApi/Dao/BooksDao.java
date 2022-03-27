package com.ariel.BooksApi.Dao;

import com.ariel.BooksApi.Model.Books;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

public interface BooksDao {

    /**
     *
     * @param dni String
     * @return ResponseEntity with List of Books
     */
    ResponseEntity<List<Books>> getAllBooks(String dni);

    /**
     *
     * @param book Books
     * @param dni String
     * @return ResponseEntity with Map of String/Object
     */
    ResponseEntity<Map<String, Object>> setBook(Books book, String dni);

    /**
     *
     * @param book Books
     * @param dni String
     * @return ResponseEntity with Map of String/Object
     */
    ResponseEntity<Map<String, Object>> updateBook(Books book, String dni);

    /**
     *
     * @param id Integer
     * @return ResponseEntity with Map of String/Object
     */
    ResponseEntity<Map<String, Object>> deleteBook(Integer id);
}
