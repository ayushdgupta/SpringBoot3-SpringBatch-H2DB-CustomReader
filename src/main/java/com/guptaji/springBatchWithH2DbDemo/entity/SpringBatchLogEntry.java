package com.guptaji.springBatchWithH2DbDemo.entity;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
public class SpringBatchLogEntry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq_id")
  private Long seqId;

  @Column(name = "job_id")
  private String jobId;

  @Column(name = "created_timestamp")
  private Timestamp createdTimestamp;

  @Column(name = "updated_timestamp")
  private Timestamp updatedTimestamp;

  @Column(name = "status")
  private String status;

  public Long getSeqId() {
    return seqId;
  }

  public void setSeqId(Long seqId) {
    this.seqId = seqId;
  }

  public String getJobId() {
    return jobId;
  }

  public void setJobId(String jobId) {
    this.jobId = jobId;
  }

  public Timestamp getCreatedTimestamp() {
    return createdTimestamp;
  }

  public void setCreatedTimestamp(Timestamp createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
  }

  public Timestamp getUpdatedTimestamp() {
    return updatedTimestamp;
  }

  public void setUpdatedTimestamp(Timestamp updatedTimestamp) {
    this.updatedTimestamp = updatedTimestamp;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
