package com.guptaji.springBatchWithH2DbDemo.util;

import static com.guptaji.springBatchWithH2DbDemo.constants.Constant.*;

import com.guptaji.springBatchWithH2DbDemo.entity.SpringBatchLogEntry;
import com.guptaji.springBatchWithH2DbDemo.entity.StudentOne;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommonUtil {

  public static List<StudentOne> createRandomData(String name, String clgName, long count) {
    List<StudentOne> randomStudentList = new ArrayList<>();
    for (long i = 0; i < count; i++) {
      StudentOne studentOne = new StudentOne();
      studentOne.setName(name + i);
      studentOne.setCollegeName(clgName + i);
      randomStudentList.add(studentOne);
    }
    return randomStudentList;
  }

  public static LocalDateTime createCurrentTimestamp() {
    return LocalDateTime.now();
  }

  public static SpringBatchLogEntry constructSpringBatchLogEntity(
      String jobId, Timestamp timestamp, String status) {
    SpringBatchLogEntry logEntry = new SpringBatchLogEntry();
    logEntry.setCreatedTimestamp(timestamp);
    logEntry.setUpdatedTimestamp(timestamp);
    logEntry.setStatus(status);
    logEntry.setJobId(jobId);
    return logEntry;
  }
}
