package com.example.final_pr.repo;

import com.example.final_pr.model.Bathroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BathroomRepository extends JpaRepository<Bathroom,Long> {


}
