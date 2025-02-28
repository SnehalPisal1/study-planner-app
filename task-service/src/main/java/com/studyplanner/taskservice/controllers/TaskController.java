package com.studyplanner.taskservice.controllers;

import com.studyplanner.taskservice.models.Task;
import com.studyplanner.taskservice.services.TaskServicesImpl;
import jakarta.validation.Valid;
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

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable long taskId){

        boolean exists = taskServicesImpl.findTask(taskId);

        Map<String, String> response = new HashMap<>();
        if(!exists){
            response.put("message","TASK not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        else {
            response.put("message","TASK SUCCESSFULLY DELETED");
            taskServicesImpl.deleteTask(taskId);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }

    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId, @Valid @RequestBody Task task) {
        try {
            // Ensure the path ID matches the task ID to prevent confusion
            if (!taskId.equals(task.getTaskId())) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Task ID in path does not match task ID in request body"));
            }

            Task updatedTask = taskServicesImpl.updateTask(task);

            if (updatedTask != null) {
                return ResponseEntity.ok(updatedTask);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Task not found or failed to update"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error updating task: " + e.getMessage()));
        }
    }
}
