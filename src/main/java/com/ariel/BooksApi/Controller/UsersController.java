package com.ariel.BooksApi.Controller;

import com.ariel.BooksApi.Dao.UsersDao;
import com.ariel.BooksApi.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", methods={RequestMethod.POST, RequestMethod.OPTIONS}, maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersDao usersDao;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> Register(@RequestBody Users user){
        return this.usersDao.register(user);
    }

    @PostMapping("/auth")
    public ResponseEntity<Map<String, Object>> Auth(@RequestBody Users user){
        return this.usersDao.auth(user);
    }
}
