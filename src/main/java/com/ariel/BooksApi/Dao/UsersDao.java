package com.ariel.BooksApi.Dao;

import com.ariel.BooksApi.Model.Users;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UsersDao {

    /**
     *
     * @param user Users
     * @return ResponseEntity with Map of String/Object
     */
    ResponseEntity<Map<String, Object>> register(Users user);

    /**
     *
     * @param user Users
     * @return ResponseEntity with Map of String/Object
     */
    ResponseEntity<Map<String, Object>> auth(Users user);
}
