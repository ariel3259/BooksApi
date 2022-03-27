package com.ariel.BooksApi.Controller;

import com.ariel.BooksApi.Dao.BooksDao;
import com.ariel.BooksApi.Model.Books;
import com.ariel.BooksApi.Utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods={ RequestMethod.GET,RequestMethod.POST,  RequestMethod.PUT,RequestMethod.DELETE,  RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/books")
public class BooksController {

    @Autowired
    private BooksDao booksDao;

    @Autowired
    private Token jwt;

    private Map<String, Object> serverError;

    @GetMapping
    public ResponseEntity<List<Books>> getBooks(@RequestHeader(name = "subject") String subject, @RequestHeader("authorization") String token){
        if(!jwt.verifyToken(token, subject)){
            return ResponseEntity.status(401).body(null);
        }
        return this.booksDao.getAllBooks(subject);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> postBook(@RequestBody Books book, @RequestHeader(name = "subject") String subject, @RequestHeader("authorization") String token){
        this.serverError = new HashMap<>();
        if(!jwt.verifyToken(token ,subject)){
            this.serverError.put("message", "You're not logged in");
            return ResponseEntity.status(401).body(this.serverError);
        }
        return this.booksDao.setBook(book, subject);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> putBook(@RequestBody Books book, @RequestHeader(name = "subject") String subject, @RequestHeader("authorization") String token){
        this.serverError = new HashMap<>();
        if(!jwt.verifyToken(token ,subject)){
            this.serverError.put("message", "You're not logged in");
            return ResponseEntity.status(401).body(this.serverError);
        }
        return this.booksDao.updateBook(book, subject);
    }

    @DeleteMapping
    public ResponseEntity<Map<String,Object>> deleteBook(@RequestHeader(name = "id") Integer id, @RequestHeader(name = "subject") String subject, @RequestHeader("authorization") String token){
        this.serverError = new HashMap<>();
        if(!jwt.verifyToken(token ,subject)){
            this.serverError.put("message", "You're not logged in");
            return ResponseEntity.status(401).body(this.serverError);
        }
        return this.booksDao.deleteBook(id);
    }
}
