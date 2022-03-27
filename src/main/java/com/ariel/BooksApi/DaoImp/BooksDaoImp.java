package com.ariel.BooksApi.DaoImp;

import com.ariel.BooksApi.Dao.BooksDao;
import com.ariel.BooksApi.Model.Books;
import com.ariel.BooksApi.Model.Users;
import com.ariel.BooksApi.Repository.BooksRepository;
import com.ariel.BooksApi.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BooksDaoImp implements BooksDao {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private UsersRepository usersRepository;

    private Map<String, Object> serverResponse;

    @Override
    public ResponseEntity<List<Books>> getAllBooks(String dni) {

        //Incomplete data
        if(dni == null){
            return ResponseEntity.status(400).body(null);
        }

        //Find User
        Optional<Users> author = this.usersRepository.findByDni(dni);

        //The user doesn't exits
        if(author.isEmpty()){
            return ResponseEntity.status(401).body(null);
        }

        //Response
        return ResponseEntity.status(200).body(this.booksRepository.findByAuthorAndState(author.get(), true));
    }

    @Override
    public ResponseEntity<Map<String, Object>> setBook(Books book, String dni) {
        //Initializing serverResponse
        this.serverResponse = new HashMap<>();

        if(book.isEmpty() || dni.isEmpty()){
            this.serverResponse.put("message", "Incomplete data");
            return ResponseEntity.status(400).body(this.serverResponse);
        }

        //Find User
        Optional<Users> author = this.usersRepository.findByDni(dni);

        //The user doesn't exits
        if(author.isEmpty()){
            this.serverResponse.put("message", "The user does't exits");
            return ResponseEntity.status(401).body(this.serverResponse);
        }

        //Aggregate user and state to book
        book.setAuthor(author.get());
        book.setState(true);

        //Saving book
        this.booksRepository.save(book);

        //Response
        this.serverResponse.put("message", "Book saved");
        return ResponseEntity.status(200).body(this.serverResponse);
    }

    @Override
    public ResponseEntity<Map<String, Object>> updateBook(Books book, String dni) {
        //Initializing serverResponse
        this.serverResponse = new HashMap<>();

        //Incomplete data
        if(book.isEmpty() || dni.isEmpty()){
            this.serverResponse.put("message", "Incomplete data");
            return ResponseEntity.status(400).body(this.serverResponse);
        }

        //Find user
        Optional<Users> author = this.usersRepository.findByDni(dni);

        //The user doesn't exits
        if(author.isEmpty()){
            this.serverResponse.put("message", "The user doesn't exits");
            return ResponseEntity.status(401).body(this.serverResponse);
        }

        //Aggregate author and state to book
        book.setAuthor(author.get());
        book.setState(true);

        //Update book
        this.booksRepository.save(book);

        //Response
        this.serverResponse.put("message", "Book modified");
        return ResponseEntity.status(200).body(this.serverResponse);
    }

    @Override
    public ResponseEntity<Map<String, Object>> deleteBook(Integer id) {
        //Initializing serverResponse
        this.serverResponse = new HashMap<>();

        //Incomplete data
        if(id == null){
            this.serverResponse.put("message", "Incomplete data");
            return ResponseEntity.status(400).body(this.serverResponse);
        }

        //Find book
        Optional<Books> bookToDelete = this.booksRepository.findById(id);

        //Book doesn't exits
        if(bookToDelete.isEmpty()){
            this.serverResponse.put("message", "Book doesn't exits");
            return ResponseEntity.status(400).body(this.serverResponse);
        }

        //Deleting book
        bookToDelete.get().setState(false);
        this.booksRepository.save(bookToDelete.get());

        //Response
        this.serverResponse.put("message", "Book deleted");
        return ResponseEntity.status(200).body(this.serverResponse);
    }
}
