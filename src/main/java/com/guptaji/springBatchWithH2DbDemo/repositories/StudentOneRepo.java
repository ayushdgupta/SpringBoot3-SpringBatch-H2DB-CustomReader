package com.guptaji.springBatchWithH2DbDemo.repositories;

import com.guptaji.springBatchWithH2DbDemo.entity.StudentOne;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentOneRepo extends JpaRepository<StudentOne, Long> {}
