package com.guptaji.springBatchWithH2DbDemo.controller;

import com.guptaji.springBatchWithH2DbDemo.service.StudentOneService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dataCreator")
public class DataCreator {

  Logger LOG = LogManager.getLogger(DataCreator.class);

  @Autowired private StudentOneService studentOneService;

  @PostMapping("/createData/{name}/{clgName}/{count}")
  public ResponseEntity<?> createData(
      @PathVariable("name") String name,
      @PathVariable("clgName") String clgName,
      @PathVariable("count") long count) {
    studentOneService.createData(name, clgName, count);
    LOG.info("Created data");
    return ResponseEntity.status(HttpStatus.OK).body("Created the data");
  }
}
