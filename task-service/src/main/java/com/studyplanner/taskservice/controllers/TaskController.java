package com.studyplanner.taskservice.controllers;

import com.studyplanner.taskservice.models.Task;
import com.studyplanner.taskservice.services.TaskServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TaskController {

    @Autowired
    TaskServicesImpl taskServicesImpl;

    @PostMapping("/tasks")
    public ResponseEntity<?> createTask(@RequestBody Task task){

        Task response = taskServicesImpl.createTask(task);
        if(response != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        else {

            Map<String,String> errorResponse= new HashMap<>();

            errorResponse.put("message" ,"Failed to create task. Please check your input.");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse);
        }
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTasks() {
        List<Task> response = taskServicesImpl.getAllTasks();
        if (response != null && !response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {

            Map<String, String> errorResponse = new HashMap<>();

            errorResponse.put("message", "Tasks Not Found");

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorResponse);
        }

    }

}
