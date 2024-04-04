package com.guptaji.springBatchWithH2DbDemo.repositories;

import com.guptaji.springBatchWithH2DbDemo.entity.StudentTwo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentTwoRepo extends JpaRepository<StudentTwo, Long> {}
