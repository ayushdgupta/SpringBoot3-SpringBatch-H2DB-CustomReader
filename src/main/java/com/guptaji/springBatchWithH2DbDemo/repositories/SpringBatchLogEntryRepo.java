package com.guptaji.springBatchWithH2DbDemo.repositories;

import com.guptaji.springBatchWithH2DbDemo.entity.SpringBatchLogEntry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringBatchLogEntryRepo extends JpaRepository<SpringBatchLogEntry, Long> {}
