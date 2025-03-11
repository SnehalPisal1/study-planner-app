package com.studyplanner.taskservice.services;

import com.studyplanner.taskservice.models.Task;
import com.studyplanner.taskservice.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServicesImpl implements TaskServices {

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
    public void deleteTask(long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public boolean findTask(long taskId) {
        return taskRepository.existsById(taskId);
    }

    @Override
    @Transactional
    public Task updateTask(Task task) {

       long taskId= task.getTaskId();

       Optional<Task> optional= taskRepository.findById(taskId);

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
