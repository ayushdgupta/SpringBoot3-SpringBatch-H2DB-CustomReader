package com.guptaji.springBatchWithH2DbDemo;

import static com.guptaji.springBatchWithH2DbDemo.constants.Constant.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBatchWithH2DbDemoApplication implements CommandLineRunner {

  Logger LOG = LogManager.getLogger(SpringBatchWithH2DbDemoApplication.class);

  @Autowired private JobLauncher jobLauncher;

  @Autowired
  @Qualifier("studentJob")
  private Job job;

  public static void main(String[] args) {
    SpringApplication.run(SpringBatchWithH2DbDemoApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    String jobId = UUID.randomUUID().toString();
    JobParameters parameters =
        new JobParametersBuilder()
            .addString(JOB_ID, jobId)
            .addLocalDateTime("startAt", LocalDateTime.now())
            .toJobParameters();

    //    ThreadContext.put(CORRELATION_ID, jobId);
    LOG.info("Starting the student job with params {}", parameters);

    try {
      JobExecution execution = jobLauncher.run(job, parameters);
      if (COMPLETED_STATUS.equalsIgnoreCase(String.valueOf(execution.getStatus()))) {
        LOG.info("Job completed successfully");
        //        ThreadContext.clearAll();
        System.exit(0);
      } else {
        LOG.error("Exception encountered during student job execution");
        //        ThreadContext.clearAll();
        System.exit(1);
      }
    } catch (Exception e) {
      LOG.error(
          "Exception encountered during student job execution and the exception message is {} and the exception object is {}",
          e.getMessage(),
          e);
      //      ThreadContext.clearAll();
      System.exit(1);
    }
  }
}
