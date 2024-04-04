package com.guptaji.springBatchWithH2DbDemo.constants;

public class Constant {

  public static final String COUNT_QUERY = "select count(*) from public.student_one";
  public static final String DATA_EXTRACTION_QUERY = "select * from public.student_one order by id";
  public static final String DATA_COUNT = "count";
  public static final String FINAL_QUERY = "finalQuery";
  public static final String OFFSET = "offset";
  public static final String LIMIT = "limit";
  public static final String IS_COMPLETED = "isCompleted";
  public static final String PARTITION_NO = "partitionNo";
  public static final String COMPLETED_STATUS = "COMPLETED";
  public static final String ERROR_STATUS = "ERROR";
  public static final String INPROGRESS = "INPROGRESS";
  public static final String CORRELATION_ID = "correlationId";
  public static final String JOB_ID = "jobId";
  public static final String SEQ_ID = "seqId";
  public static final String TIMESTAMP = "currentTimestamp";
}
