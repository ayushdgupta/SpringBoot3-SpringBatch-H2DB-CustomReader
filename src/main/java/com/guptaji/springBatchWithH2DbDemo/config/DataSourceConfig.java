package com.guptaji.springBatchWithH2DbDemo.config;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;

import lombok.SneakyThrows;

@Configuration
public class DataSourceConfig extends DefaultBatchConfiguration {

  Logger LOG = LogManager.getLogger(DataSourceConfig.class);

  @Value("${spring.postgresql.username}")
  public String dbUserName;

  @Value("${spring.postgresql.url}")
  public String dbUrl;

  @Value("${spring.postgresql.password}")
  public String dbPass;

  @Value("${spring.postgresql.driver-class-name}")
  public String dbDriver;

  // creating a datasource bean to use postgre db
  @Bean
  @Primary
  @Qualifier("postgreDataSource")
  public DataSource dataSource() {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(dbUrl);
    dataSource.setUsername(dbUserName);
    dataSource.setPassword(dbPass);
    dataSource.setDriverClassName(dbDriver);
    dataSource.setConnectionTimeout(100000000000000000L);
    dataSource.setMaximumPoolSize(200);
    return dataSource;
  }

  @Bean
  @BatchDataSource
  public DataSource h2DbDataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
    return embeddedDatabaseBuilder
        .addScript("classpath:/config/h2Script.sql")
        .setType(EmbeddedDatabaseType.H2)
        .build();
  }

  @SneakyThrows
  @Bean(name = "jobRepository")
  public JobRepository jobRepository() {
    JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
    factory.setDataSource(h2DbDataSource());
    factory.setTransactionManager(new ResourcelessTransactionManager());
    factory.setIsolationLevelForCreate("ISOLATION_READ_UNCOMMITTED");
    factory.setTablePrefix("BATCH_");
    factory.afterPropertiesSet();
    return factory.getObject();
  }

  @Bean
  public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
    return new NamedParameterJdbcTemplate(dataSource());
  }

  @Bean(name = "transactionAttribute")
  public TransactionAttribute transactionAttribute() {
    DefaultTransactionAttribute attribute = new DefaultTransactionAttribute();
    attribute.setPropagationBehavior(Propagation.REQUIRED.value());
    attribute.setIsolationLevel(Isolation.READ_UNCOMMITTED.value());
    return attribute;
  }
}
