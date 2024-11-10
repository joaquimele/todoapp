package com.workshop.todoapp.service;

import com.workshop.todoapp.model.Task;
import com.workshop.todoapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    TaskRepository taskRepository;

   @Autowired
   public TaskService(TaskRepository taskRepository){
       this.taskRepository = taskRepository;
   }

   public List<Task> getAllTask (){
        return taskRepository.findAll();
   }

   public Optional<Task> getTaskByID (Long id){
       return taskRepository.findById(id);
   }

   public Task createTask (Task task){
        return taskRepository.save(task);
   }

   public Task updateTask (Long id, Task taskDetails){
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(taskDetails.getTitle());
                    task.setDescription(taskDetails.getDescription());
                    task.setCompleted(taskDetails.isCompleted());
                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id ));
   }

   public void deleteTask (Long id){
       taskRepository.deleteById(id);
   }
}
