package com.project.backend.repository;

import com.project.backend.model.AppError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppErrorRepository extends JpaRepository<AppError, Long> {
}
