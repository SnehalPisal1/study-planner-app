package com.studyplanner.taskservice.services;

import com.studyplanner.taskservice.models.Task;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@EnableAsync
public interface TaskServices {

    Task createTask(Task task);

    @Async("taskExecutor")
    CompletableFuture<List<Task>> getAllTasks(String userName);

    void deleteTask(long taskId);

    boolean findTask(long taskId);

    Task updateTask(Task task);
}
