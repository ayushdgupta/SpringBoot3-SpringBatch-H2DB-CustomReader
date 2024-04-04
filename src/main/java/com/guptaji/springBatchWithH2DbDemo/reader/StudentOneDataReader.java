package com.guptaji.springBatchWithH2DbDemo.reader;

import static com.guptaji.springBatchWithH2DbDemo.constants.Constant.*;

import com.guptaji.springBatchWithH2DbDemo.entity.StudentOne;
import com.guptaji.springBatchWithH2DbDemo.mapper.StudentOneRowMapper;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@StepScope
@Component
public class StudentOneDataReader implements ItemReader<List<StudentOne>>, StepExecutionListener {

  Logger LOG = LogManager.getLogger(StudentOneDataReader.class);

  @Autowired private NamedParameterJdbcTemplate jdbcTemplate;

  private String query;
  private long limit;
  private long offset;
  private StepExecution stepExecution;

  /**
   * @return
   * @throws Exception
   * @throws UnexpectedInputException
   * @throws ParseException
   * @throws NonTransientResourceException
   *     <p>Here we used one flag isCompleted because once this step will execute completely in a
   *     thread then it'll not stop automatically, it'll again come to execute this with same query
   *     and the data will be available in the db with the same offset and limits so it'll again
   *     execute and then again it'll come, this is happening because the thread or grid will not
   *     stop automatically, they stop when reader returns null from his side, so once we read the
   *     data, then we will set this flag to be tru so that next time when again grid will come with
   *     the same query within the same context, it'll return null.
   */
  @Override
  public List<StudentOne> read()
      throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    boolean isCompleted = (boolean) this.stepExecution.getExecutionContext().get(IS_COMPLETED);
    if (!isCompleted) {
      String offsetClause = " LIMIT " + limit + " OFFSET " + offset;
      if (!query.contains(offsetClause)) {
        query = query + offsetClause;
      }

      // Here also we can convert the studentOneObject into StudentTwo
      List<StudentOne> studentOneList = jdbcTemplate.query(query, new StudentOneRowMapper());
      stepExecution.getExecutionContext().put(IS_COMPLETED, true);
      LOG.info("Query executed {} and it fetches {} rows", query, studentOneList.size());
      return studentOneList;
    }
    return null;
  }

  /**
   * Here we will extract all the necessary information like limit and offset from
   * 'StepExecutionContext' which we have assigned in 'StudentPartitioner' to extract a separate
   * data in all queries.
   */
  @Override
  public void beforeStep(StepExecution stepExecution) {
    this.stepExecution = stepExecution;

    // Note: Here we are extracting the data from 'Step Execution Context'
    offset = stepExecution.getExecutionContext().getLong(OFFSET);
    limit = stepExecution.getExecutionContext().getLong(LIMIT);
    query = stepExecution.getJobExecution().getExecutionContext().getString(FINAL_QUERY);
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    return null;
  }
}
