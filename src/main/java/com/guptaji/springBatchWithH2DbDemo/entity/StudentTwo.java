package com.guptaji.springBatchWithH2DbDemo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class StudentTwo {

  @Id private Long id;

  private String name;
  private String collegeName;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCollegeName() {
    return collegeName;
  }

  public void setCollegeName(String collegeName) {
    this.collegeName = collegeName;
  }
}