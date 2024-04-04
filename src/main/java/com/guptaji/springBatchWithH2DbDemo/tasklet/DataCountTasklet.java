package com.guptaji.springBatchWithH2DbDemo.tasklet;

import static com.guptaji.springBatchWithH2DbDemo.constants.Constant.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@StepScope
@Component
public class DataCountTasklet implements Tasklet, StepExecutionListener {

  Logger LOG = LogManager.getLogger(DataCountTasklet.class);

  private StepExecution stepExecution;

  @Autowired private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {

    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    LOG.info("Executing count query {}", COUNT_QUERY);
    Long count =
        namedParameterJdbcTemplate.queryForObject(COUNT_QUERY, mapSqlParameterSource, Long.class);
    LOG.info("No. of records to process {}", count);

    // NOTE: here we are using JobExecutionContext
    this.stepExecution.getJobExecution().getExecutionContext().putLong(DATA_COUNT, count);

    // Here we don't need to change the query so we are putting that value directly in the job
    // execution
    // context but if we want to do any change in the query then we can do those here or
    // we can create a separate tasklet like this or even there is no need to put the query in the
    // job execution context, if no changes needed.
    this.stepExecution
        .getJobExecution()
        .getExecutionContext()
        .putString(FINAL_QUERY, DATA_EXTRACTION_QUERY);
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
