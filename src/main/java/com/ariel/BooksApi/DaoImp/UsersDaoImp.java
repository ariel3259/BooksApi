package com.ariel.BooksApi.DaoImp;


import com.ariel.BooksApi.Dao.UsersDao;
import com.ariel.BooksApi.Model.Users;
import com.ariel.BooksApi.Repository.UsersRepository;
import com.ariel.BooksApi.Utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UsersDaoImp implements UsersDao {

    @Autowired
    private UsersRepository usersRepository;

    private Map<String, Object> serverResponse;


    @Override
    public ResponseEntity<Map<String, Object>> register(Users user) {
        //Initializing serverResponse
        this.serverResponse = new HashMap<>();

        //Invalid Data
        if(user.isEmpty()){
            this.serverResponse.put("message", "Incomplete data");
            return ResponseEntity.status(400).body(this.serverResponse);
        }

        //Invalid Email
        if(!user.isAValidEmail()){
            this.serverResponse.put("message", "Invalid email");
            return ResponseEntity.status(400).body(this.serverResponse);
        }

        //Hashing password
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));

        //Try Save user
        try{

            //Saving user
            this.usersRepository.save(user);

            //Response
            this.serverResponse.put("message", "Your account has been created");
            return ResponseEntity.status(200).body(this.serverResponse);
        }
        /*Trows exception if the user already exist*/
        catch (Exception e){
            //Response
            this.serverResponse.put("message", "The user already exits");
            return ResponseEntity.status(400).body(this.serverResponse);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> auth(Users user) {
        //Initializing serverResponse
        this.serverResponse = new HashMap<>();

        Token jwt = new Token();

        //Incomplete data
        if (user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            this.serverResponse.put("message", "Incomplete data");
            return ResponseEntity.status(400).body(this.serverResponse);
        }

        //Invalid Email
        if (!user.isAValidEmail()) {
            this.serverResponse.put("message", "Invalid email");
            return ResponseEntity.status(400).body(this.serverResponse);
        }

        //Finding User
        Optional<Users> validateUser = this.usersRepository.findByEmail(user.getEmail());

        //If the user doesn't exits
        if (validateUser.isEmpty()) {
            this.serverResponse.put("message", "The user doesn't exits");
            return ResponseEntity.status(401).body(this.serverResponse);
        }

        //Validating password
        boolean resultAuth = BCrypt.checkpw(user.getPassword(), validateUser.get().getPassword());

        //Wrong password
        if(!resultAuth){
            this.serverResponse.put("message", "Wrong password");
            return ResponseEntity.status(401).body(this.serverResponse);
        }

        //Response
        this.serverResponse.put("message", "Welcome "+validateUser.get().getName()+" "+validateUser.get().getLastName());
        this.serverResponse.put("access_token", jwt.getToken(validateUser.get().getDni()));
        this.serverResponse.put("subject", validateUser.get().getDni());
        return ResponseEntity.status(200).body(this.serverResponse);
    }
}
