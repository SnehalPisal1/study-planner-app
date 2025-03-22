package com.studyplanner.taskservice.controllers;

import com.studyplanner.taskservice.SecurityUtility.JwtUtil;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Task Management API", description = "API for managing tasks")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    public static Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
    @Autowired
    TaskServicesImpl taskServicesImpl;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping
    @Operation(
            summary = "Create a new task",
            description = "Creates a task with due date"
    )
    @ApiResponse(responseCode = "201", description = "Task created successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            Authentication auth= SecurityContextHolder.getContext().getAuthentication();
            String userName = auth.getName();
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

    @GetMapping
    @Operation(
            summary = "Get a all task",
            description = "Get a all task for specified user"
    )
    @ApiResponse(responseCode = "200", description = "Retrieve a list of all tasks")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public ResponseEntity<?> getAllTasks() {

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userName =  auth.getName();;
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

    @DeleteMapping("/{taskId}")
    @Operation(
            summary = "Delete task ",
            description = "Delete task from task List"
    )
    @ApiResponse(responseCode = "200", description = "Task deleted successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public ResponseEntity<?> deleteTask(@PathVariable long taskId) {
        try {
            boolean exists = taskServicesImpl.findTask(taskId);
            Map<String, String> response = new HashMap<>();
            if (!exists) {
                response.put("message", "Task not found");
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

    @PutMapping("/{taskId}")
    @Operation(
            summary = "Update an existing task",
            description = "Modify task details such as title, description, due date, or status using the task's unique ID. Returns the updated task."
    )
    @ApiResponse(responseCode = "200", description = "Task updated successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId, @Valid @RequestBody Task task) {
        try {

            if (!taskServicesImpl.findTask(taskId)) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Incorrect Task ID"));
            }

            task.setTaskId(taskId);
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
