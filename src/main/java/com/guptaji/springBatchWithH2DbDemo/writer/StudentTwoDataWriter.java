package com.guptaji.springBatchWithH2DbDemo.writer;

import static com.guptaji.springBatchWithH2DbDemo.constants.Constant.*;

import com.guptaji.springBatchWithH2DbDemo.entity.StudentTwo;
import com.guptaji.springBatchWithH2DbDemo.repositories.StudentTwoRepo;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@StepScope
@Component
public class StudentTwoDataWriter implements ItemWriter<List<StudentTwo>>, StepExecutionListener {

  Logger LOG = LogManager.getLogger(StudentTwoDataWriter.class);

  private StepExecution stepExecution;

  @Autowired private StudentTwoRepo studentTwoRepo;

  @Override
  public void write(Chunk<? extends List<StudentTwo>> chunk) throws Exception {
    List<? extends List<StudentTwo>> items = chunk.getItems();
    //    LOG.info("Writer received {} lists to write", items.size());    // this is always giving 1
    // size
    items.forEach(
        item -> {
          studentTwoRepo.saveAll(item);
          LOG.debug("Writer received {} lists to write", item.size());
        });
    LOG.debug(
        "Saved the data into DB for the partition {}",
        this.stepExecution.getExecutionContext().getString(PARTITION_NO));
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
