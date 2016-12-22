package com.sandjelkovic.dispatchd.data.repositories;


import com.sandjelkovic.dispatchd.data.entities.ImportStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportStatusRepository extends JpaRepository<ImportStatus, Long> {
}
