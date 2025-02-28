package com.studyplanner.taskservice.services;

import com.studyplanner.taskservice.models.Task;
import com.studyplanner.taskservice.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServicesImpl implements TaskServices {

    @Autowired(required = true)
    TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {
       return taskRepository.save(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public void deleteTask(long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public boolean findTask(long taskId) {
        return taskRepository.existsById(taskId);
    }

    @Override
    public Task updateTask(Task task) {

        return null;
    }

}
