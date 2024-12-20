package com.workshop.todoapp.service;

import com.workshop.todoapp.exceptions.TaskNotFoundException;
import com.workshop.todoapp.exceptions.TasksNotFoundException;
import com.workshop.todoapp.model.Task;
import com.workshop.todoapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    @Value("${no.task.found.by.id}")
    private String getNoTaskFoundByIdMessage;

    @Value("${no.task.found.message}")
    private String getNoTasksFoundMessage;

    private final TaskRepository taskRepository;

   @Autowired
   public TaskService(TaskRepository taskRepository){
       this.taskRepository = taskRepository;
   }

    public List<Task> getAllTasks() {
        LOGGER.info("Searching all tasks generated by user...");
        List<Task> allTasks = taskRepository.findAll();

        if (allTasks.isEmpty()) {
            LOGGER.error("No tasks found");
            throw new TasksNotFoundException(getNoTasksFoundMessage);
        } else {
            LOGGER.info("Found {} tasks", allTasks.size());
            return allTasks;
        }
    }

   public Optional<Task> getTaskByID (Long id){
       LOGGER.info("Searching task by ID: {}", id);
       Optional<Task> oneTaskSearch = taskRepository.findById(id);

       if(oneTaskSearch.isEmpty()){
           LOGGER.error("No task found with ID: {}", id);
           throw new TaskNotFoundException(getNoTaskFoundByIdMessage);
       } else {
           LOGGER.info("Found task with ID: {}", id);
           return oneTaskSearch;
       }
   }

    public Task createTask(Task task) {
        if(taskRepository.existsById(task.getId())){
            LOGGER.error("Task with ID {} already exists", task.getId());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Task with same id already exists");
        } else {
            LOGGER.info("Creating new task: {}", task);
            return taskRepository.save(task);
        }
    }

   public Task updateTask (Long id, Task taskDetails){
        LOGGER.info("Updating task with ID: {}", id);
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(taskDetails.getTitle());
                    task.setDescription(taskDetails.getDescription());
                    task.setCompleted(taskDetails.getCompleted());
                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new TaskNotFoundException("The following task with the id " + id + " not exist!"));
   }

   public void deleteTask (Long id){
       LOGGER.info("Deleting task with ID: {}", id);
       if(taskRepository.existsById(id)){
       taskRepository.deleteById(id);
       }else{
           LOGGER.error("Error while deleting task with ID: {}", id);
           throw new TaskNotFoundException(getNoTaskFoundByIdMessage);
       }
   }
}
