package com.lovankydev.spring_project_crud_with_sqlserver.repository;

import com.lovankydev.spring_project_crud_with_sqlserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

//    Require username is unique
    boolean existsByUserName(String userName);
    User findByUserName(String userName);
}
