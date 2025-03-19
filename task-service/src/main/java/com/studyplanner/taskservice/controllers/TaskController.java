package com.studyplanner.taskservice.controllers;

import com.studyplanner.taskservice.configs.JwtUtil;
import com.studyplanner.taskservice.models.Task;
import com.studyplanner.taskservice.services.TaskServicesImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Tag(name = "Task Management API", description = "API for managing tasks")
public class TaskController {

    public static Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
    @Autowired
    TaskServicesImpl taskServicesImpl;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/tasks")
    @Operation(
            summary = "Create a new task",
            description = "Creates a task with due date"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "201", description = "Task created successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public ResponseEntity<?> createTask(@RequestBody Task task, @RequestHeader("Authorization") String token) {
        try {
            String userName = extractUserNameFromToken(token);
            task.setCreatedBy(userName);
            Task response = taskServicesImpl.createTask(task);
            if (response != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "Failed to create task. Please check your input.");
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(errorResponse);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("Message", e.getMessage()));

        }
    }

    @Operation(
            summary = "Get a all task",
            description = "Get a all task for specified user"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "200", description = "Retrieve a list of all tasks")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTasks(@RequestHeader("Authorization") String token) {

        try {
            String userName = extractUserNameFromToken(token);
            List<Task> response = taskServicesImpl.getAllTasks(userName);
            if (response != null && !response.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "Tasks Not Found");
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(errorResponse);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("Message", e.getMessage()));
        }
    }

    @Operation(
            summary = "Delete task ",
            description = "Delete task from task List"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "200", description = "Task deleted successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable long taskId, @RequestHeader("Authorization") String token) {

        try {
            boolean exists = taskServicesImpl.findTask(taskId);
            Map<String, String> response = new HashMap<>();
            if (!exists) {
                response.put("message", "TASK not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            } else {
                response.put("message", "TASK SUCCESSFULLY DELETED");
                taskServicesImpl.deleteTask(taskId);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("Message", e.getMessage()));
        }

    }

    @Operation(
            summary = "Update an existing task",
            description = "Modify task details such as title, description, due date, or status using the task's unique ID. Returns the updated task."
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "200", description = "Task updated successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId, @Valid @RequestBody Task task,
                                        @RequestHeader("Authorization") String token) {
        try {
            // Check authentication first
            if (token == null || token.isEmpty() || !jwtUtil.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Authentication required"));
            }

            if (!taskServicesImpl.findTask(taskId)) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Incorrect Task ID"));
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

    private String extractUserNameFromToken(String token) {
        String jwt = token.replace("Bearer ", "");
        return jwtUtil.extractUsername(jwt);
    }
}
