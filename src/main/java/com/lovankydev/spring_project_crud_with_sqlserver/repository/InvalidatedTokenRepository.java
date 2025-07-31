package com.lovankydev.spring_project_crud_with_sqlserver.repository;

import com.lovankydev.spring_project_crud_with_sqlserver.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
}
