package com.example.job.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.job.dto.UserDTO;
import com.example.job.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    @Query("SELECT new com.example.job.dto.UserDTO(u.id, u.password, u.person.fullname, u.person.nickname, u.person.phone, u.role.name)"
            + "FROM Users u JOIN u.role r JOIN u.person r WHERE u.email = :email")
    UserDTO getUsers(@Param(value = "email") String email);

    @Query("SELECT u FROM Users u WHERE u.email = :email")
    Optional<Users> findByEmail(@Param(value = "email") String email);

    @Query("SELECT u FROM Users u WHERE u.email = :email")
    Users findUser(@Param(value = "email") String email);

    @Query("SELECT u.password FROM Users u WHERE u.id = :id")
    Users findPass(@Param(value = "id") Integer id);

    @Query("SELECT u.id FROM Users u WHERE u.email = :email")
    Integer findId(@Param(value = "email") String email);
}
