### Spring Batch example with customizing DataSource Config for spring batch meta data

## Overview
1. Here we are simply reading the data from studentOne table and inserting it into another table stdentTwo using spring batch.
2. But earlier we were using postgre database for everything like both studnet tables stored in postgre db and spring batch also updating it's meta data into it's internal tables which were created into postgre DB only - [Spring batch using custom reader](https://github.com/ayushdgupta/SpringBoot3-SpringBatchDemo-Using-CustomReader)
3. But sometime what happened is our main db (using for application logic) has lot of load so sometimes we need to use spring internal DB (H2) or maybe some other database to do spring batch internal transaction, we don't want our main db to handle spring internal updates, so for that we will override the default datasource config for **JobRepository** and **PlatformTransactionManager**.
4. There are two ways to provide custom config of datasource for JobRepository -
   1. @EnableBatchProcessing [Doc Link](https://docs.spring.io/spring-batch/reference/job/configuring-repository.html) e.g.
   ```
   @Configuration
   @EnableBatchProcessing(
     dataSourceRef = "batchDataSource",
     transactionManagerRef = "batchTransactionManager",
     tablePrefix = "BATCH_",
     isolationLevelForCreate = "SERIALIZABLE")
   public class MyJobConfiguration {

     // job definition
     // JOB Repositoy Definition
     @Bean
     public JobRepository jobRepository() throws Exception {
       JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
       factory.setDataSource(dataSource);
       factory.setDatabaseType("db2");
       factory.setTransactionManager(transactionManager);
       return factory.getObject();
     }
   }
   ```
   2. By extending class **'DefaultBatchConfiguration'**.
5. Remember one thing both @EnableBatchProcessing and DefaultBatchConfiguration can n't be used together at a time only one strategy should be used in the code.
6. For more info please refer following references -
   1. https://docs.spring.io/spring-batch/reference/job/java-config.html
   2. https://spring.io/blog/2022/09/22/spring-batch-5-0-0-m6-and-4-3-7-are-out
   3. https://medium.com/@aryan.shinde.29/configuring-dual-data-sources-in-spring-batch-5-and-spring-boot-3-8a72bc00555c