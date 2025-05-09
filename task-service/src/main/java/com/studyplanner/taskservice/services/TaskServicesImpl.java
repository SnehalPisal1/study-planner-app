package com.studyplanner.taskservice.services;

import com.studyplanner.taskservice.models.Task;
import com.studyplanner.taskservice.repositories.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class TaskServicesImpl implements TaskServices {

    public static Logger logger = LoggerFactory.getLogger(TaskServicesImpl.class);

    @Autowired
    TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {
       return taskRepository.save(task);
    }

    @Async("taskExecutor")
    @Override
    public CompletableFuture<List<Task>> getAllTasks(String userName) {
        logger.info("Executing - "+Thread.currentThread().getName());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("SecurityContext in async thread: {}", auth);

        if (auth == null) {
            logger.error("SecurityContext is null in async call! Rejecting request.");
            throw new AuthenticationException("Security context lost in async execution") {};
        }

        return CompletableFuture.completedFuture(taskRepository.findByCreatedBy(userName));
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
