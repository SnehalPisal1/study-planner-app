package com.studyplanner.taskservice.services;

import com.studyplanner.taskservice.models.Task;
import com.studyplanner.taskservice.repositories.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServicesImpl implements TaskServices {

    public static Logger logger = LoggerFactory.getLogger(TaskServicesImpl.class);

    @Autowired
    TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {
       return taskRepository.save(task);
    }

    @Override
    public List<Task> getAllTasks(String userName) {
        return taskRepository.findByCreatedBy(userName);
    }

    @Override
    public boolean deleteTask(long taskId) {
        boolean exists = taskRepository.existsById(taskId);
        if (!exists) {
            return false;
        }
        taskRepository.deleteById(taskId);
        return true;

    }

    @Override
    public boolean findTask(long taskId) {
        return taskRepository.existsById(taskId);
    }

    @Override
    @Transactional
    public Task updateTask(Task task) {

       Optional<Task> optional= taskRepository.findById(task.getTaskId());

       if(optional.isPresent()){

           Task existingTask =optional.get();
           existingTask.setTaskName(task.getTaskName());
           existingTask.setDescription(task.getDescription());
           existingTask.setStatus(task.getStatus());
           existingTask.setDueDate(task.getDueDate());

           taskRepository.save(existingTask);
           return existingTask;

       }else {
           return null; //task not found
       }

    }

}
