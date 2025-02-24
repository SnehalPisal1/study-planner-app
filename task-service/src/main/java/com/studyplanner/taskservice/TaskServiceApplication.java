package com.studyplanner.taskservice;
/*
// Project structure
// task-service
//   ├── pom.xml
//   └── src/main/java/com/studyplanner/task
//       ├── com.studyplanner.taskservice.TaskServiceApplication.java
//       ├── config/
//       │   └── SecurityConfig.java
//       ├── controller/
//       │   └── TaskController.java
//       ├── dto/
//       │   ├── TaskDTO.java
//       │   └── TaskStatusUpdateDTO.java
//       ├── model/
//       │   ├── Task.java
//       │   └── TaskPriority.java
//       ├── repository/
//       │   └── TaskRepository.java
//       └── service/
//           └── TaskService.java
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.studyplanner.taskservice.repositories")
@EntityScan("com.studyplanner.taskservice.models") // Scan entities
public class TaskServiceApplication {

    public static void main(String[] args){

        System.out.println("Executing com.studyplanner.taskservice.TaskServiceApplication....");

        SpringApplication.run(TaskServiceApplication.class, args);

    }
}
