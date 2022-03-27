package com.ariel.BooksApi.Repository;

import com.ariel.BooksApi.Model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByDni(String dni);
}
