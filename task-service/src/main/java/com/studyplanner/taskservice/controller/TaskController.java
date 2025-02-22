package com.studyplanner.taskservice.controller;

import com.studyplanner.taskservice.model.Task;
import com.studyplanner.taskservice.service.TaskServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        else{

            return null;

        }
    }



}
