package com.example.final_pr.repo;

import com.example.final_pr.model.Flat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlatRepository extends JpaRepository<Flat,Long> {

}
