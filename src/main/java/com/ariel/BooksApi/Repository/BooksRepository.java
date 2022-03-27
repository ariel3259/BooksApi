package com.ariel.BooksApi.Repository;

import com.ariel.BooksApi.Model.Books;
import com.ariel.BooksApi.Model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends CrudRepository<Books, Integer> {
    List<Books> findByAuthorAndState(Users user, boolean state);
}
