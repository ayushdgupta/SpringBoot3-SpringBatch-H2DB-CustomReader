package com.guptaji.springBatchWithH2DbDemo.service;

import com.guptaji.springBatchWithH2DbDemo.entity.StudentOne;
import com.guptaji.springBatchWithH2DbDemo.repositories.StudentOneRepo;
import com.guptaji.springBatchWithH2DbDemo.util.CommonUtil;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentOneService {

  Logger LOG = LogManager.getLogger(StudentOneService.class);

  @Autowired private StudentOneRepo studentOneRepo;

  public void createData(String name, String clgName, long count) {
    List<StudentOne> randomData = CommonUtil.createRandomData(name, clgName, count);
    studentOneRepo.saveAll(randomData);
    LOG.info("Created the {} rows in db with name {} and clg name {}.", count, name, clgName);
  }
}
