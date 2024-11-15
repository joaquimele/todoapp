package com.workshop.todoapp.service;

import com.workshop.todoapp.model.Task;
import com.workshop.todoapp.repository.TaskRepository;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

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
        List<Task> allTasks = taskRepository.findAll();

        if (allTasks.isEmpty()) {
            throw new MessageDescriptorFormatException(getNoTaskFoundByIdMessage);
        } else {
            return allTasks;
        }
    }

   public Optional<Task> getTaskByID (Long id){
       Optional<Task> oneTaskSearch = taskRepository.findById(id);

       if(oneTaskSearch.isEmpty()){
           throw new MessageDescriptorFormatException(getNoTasksFoundMessage);
       } else {
           return oneTaskSearch;
       }
   }

    public Task createTask(Task task) {
        if(taskRepository.existsById(task.getId())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Task with same id already exists");
        } else {
            return taskRepository.save(task);
        }
    }

   public Task updateTask (Long id, Task taskDetails){
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(taskDetails.getTitle());
                    task.setDescription(taskDetails.getDescription());
                    task.setCompleted(taskDetails.getCompleted());
                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new RuntimeException("The following task with the id " + id + " not exist!"));
   }

   public void deleteTask (Long id){
       taskRepository.deleteById(id);
   }
}
