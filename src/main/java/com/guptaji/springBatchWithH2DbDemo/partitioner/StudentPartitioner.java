package com.guptaji.springBatchWithH2DbDemo.partitioner;

import static com.guptaji.springBatchWithH2DbDemo.constants.Constant.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@StepScope
@Component
public class StudentPartitioner implements Partitioner, StepExecutionListener {

  Logger LOG = LogManager.getLogger(StudentPartitioner.class);

  // another way to fetch the instance of StepExecution
  @Value("#{stepExecution}")
  public StepExecution stepExecution;

  private Long count;

  @Override
  public Map<String, ExecutionContext> partition(int gridSize) {
    Map<String, ExecutionContext> executionContextMap = new HashMap<>();
    count = this.stepExecution.getJobExecution().getExecutionContext().getLong(DATA_COUNT);

    // no. of records that one grid can accomodate
    int partitionSize = (int) Math.ceil((double) count / gridSize);

    for (int i = 0; i < gridSize; i++) {
      long currentMin = i * partitionSize;
      long currentMax = Math.min(currentMin + partitionSize, count);
      ExecutionContext context = new ExecutionContext();
      if (currentMin >= count) {
        // to handle negative limit case
        break;
      }
      context.putLong(OFFSET, currentMin);
      context.putLong(LIMIT, currentMax - currentMin);
      context.put(IS_COMPLETED, false);
      context.put(PARTITION_NO, "partition" + i);
      executionContextMap.put("partition" + i, context);
    }
    return executionContextMap;
  }
}
