package com.example.capstone3.Repository;

import com.example.capstone3.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    Users findUsersById(Integer id);

}
