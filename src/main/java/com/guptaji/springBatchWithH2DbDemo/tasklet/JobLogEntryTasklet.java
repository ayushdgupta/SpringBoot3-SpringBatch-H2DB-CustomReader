package com.guptaji.springBatchWithH2DbDemo.tasklet;

import static com.guptaji.springBatchWithH2DbDemo.constants.Constant.*;

import com.guptaji.springBatchWithH2DbDemo.entity.SpringBatchLogEntry;
import com.guptaji.springBatchWithH2DbDemo.repositories.SpringBatchLogEntryRepo;
import com.guptaji.springBatchWithH2DbDemo.util.CommonUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@StepScope
@Component
public class JobLogEntryTasklet implements Tasklet, StepExecutionListener {

  Logger LOG = LogManager.getLogger(JobLogEntryTasklet.class);

  private StepExecution stepExecution;

  @Autowired private SpringBatchLogEntryRepo springBatchLogEntryRepo;

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    JobExecution jobExecution = this.stepExecution.getJobExecution();
    String jobId = jobExecution.getJobParameters().getString(JOB_ID);
    LocalDateTime currentTimestamp = CommonUtil.createCurrentTimestamp();
    SpringBatchLogEntry logEntry =
        CommonUtil.constructSpringBatchLogEntity(
            jobId, Timestamp.valueOf(currentTimestamp), INPROGRESS);
    SpringBatchLogEntry savedEntity = springBatchLogEntryRepo.save(logEntry);
    LOG.info("Saved job entry with id {}", savedEntity.getSeqId());

    ExecutionContext jobExecutionContext = jobExecution.getExecutionContext();
    jobExecutionContext.putLong(SEQ_ID, savedEntity.getSeqId());
    jobExecutionContext.put(TIMESTAMP, Timestamp.valueOf(currentTimestamp));
    return RepeatStatus.FINISHED;
  }

  @Override
  public void beforeStep(StepExecution stepExecution) {
    this.stepExecution = stepExecution;
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    return null;
  }
}
