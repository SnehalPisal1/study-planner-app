package com.studyplanner.taskservice.service;

import com.studyplanner.taskservice.model.Task;
import com.studyplanner.taskservice.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServicesImpl implements TaskServices {

    @Autowired(required = true)
    TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {
       return taskRepository.save(task);
    }

}
