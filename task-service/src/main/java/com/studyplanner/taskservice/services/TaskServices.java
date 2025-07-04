package com.studyplanner.taskservice.services;

import com.studyplanner.taskservice.models.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskServices {

    Task createTask(Task task);

    List<Task> getAllTasks(String userName);

    boolean deleteTask(long taskId);

    boolean findTask(long taskId);

    Task updateTask(Task task);
}
