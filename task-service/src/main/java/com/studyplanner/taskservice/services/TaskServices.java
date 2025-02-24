package com.studyplanner.taskservice.service;

import com.studyplanner.taskservice.model.Task;
import org.springframework.stereotype.Service;

@Service
public interface TaskServices {

    Task createTask(Task task);

    List<Task> getAllTasks();

}
