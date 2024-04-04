package com.guptaji.springBatchWithH2DbDemo.processor;

import static com.guptaji.springBatchWithH2DbDemo.constants.Constant.*;

import com.guptaji.springBatchWithH2DbDemo.entity.StudentOne;
import com.guptaji.springBatchWithH2DbDemo.entity.StudentTwo;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@StepScope
@Component
public class StudentOneDataProcessor
    implements ItemProcessor<List<StudentOne>, List<StudentTwo>>, StepExecutionListener {

  Logger LOG = LogManager.getLogger(StudentOneDataProcessor.class);

  private StepExecution stepExecution;

  @Override
  public List<StudentTwo> process(List<StudentOne> item) throws Exception {
    List<StudentTwo> studentTwoList = new ArrayList<>();
    item.forEach(
        studentOne -> {
          StudentTwo studentTwo = new StudentTwo();
          studentTwo.setId(studentOne.getId());
          studentTwo.setName(studentOne.getName() + " Gupta");
          studentTwo.setCollegeName(studentOne.getCollegeName());
          studentTwoList.add(studentTwo);
        });
    LOG.debug(
        "Processed the partition {}",
        this.stepExecution.getExecutionContext().getString(PARTITION_NO));
    return studentTwoList;
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
