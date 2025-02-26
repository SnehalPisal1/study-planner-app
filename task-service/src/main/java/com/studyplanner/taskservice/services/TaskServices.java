package com.studyplanner.taskservice.services;

import com.studyplanner.taskservice.models.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskServices {

    Task createTask(Task task);

    List<Task> getAllTasks();

    void deleteTask(long taskId);

    boolean findTask(long taskId);
}
