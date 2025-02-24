package com.studyplanner.taskservice.controllers;

import com.studyplanner.taskservice.models.Task;
import com.studyplanner.taskservice.services.TaskServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    TaskServicesImpl taskServicesImpl;

    @RequestMapping ("/tasks")
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        Task response = taskServicesImpl.createTask(task);
        if(response != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();   // This returns an empty response body with BAD_REQUEST status
        }
    }

    @RequestMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasks(){

        return null;
    }



}
