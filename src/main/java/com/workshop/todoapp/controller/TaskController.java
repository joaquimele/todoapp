package com.workshop.todoapp.controller;

import com.workshop.todoapp.model.Task;
import com.workshop.todoapp.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Task", description = "Operations in function with the CRUD for task objects.")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping
    @Operation(summary = "Get all tasks", description = "Return a list of all tasks created by the user.",
    responses = {
            @ApiResponse(responseCode = "200", description = "Tasks obtained successfully. The body of the response is a JSON list of tasks in case of 200 response status or none in case of 404."),
            @ApiResponse(responseCode = "404", description = "No task was founded.")
    })
    public List<Task> getAllTask(){
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by ID", description = "Return only one task in case of 200 response status or none in case of 404.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task obtained successfully. The body of the response is a JSON with the specific task."),
                    @ApiResponse(responseCode = "404", description = "No task was founded.")
            })
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        return taskService.getTaskByID(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create task.", description = "Return 201 response for created or none in case of 400.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created task successfully."),
                    @ApiResponse(responseCode = "400", description = "Bad request while creating a task.")
            })
    public ResponseEntity<Task> createTask(@Valid  @RequestBody Task task){
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update task.", description = "Return 201 response for updated or none in case of 400.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated task successfully."),
                    @ApiResponse(responseCode = "404", description = "The ID provided don't exist.")
            })
    public ResponseEntity<Task> updateTask(@Valid @PathVariable Long id, @RequestBody Task taskDetails){
        try{
            Task updateTask = taskService.updateTask(id, taskDetails);
            return ResponseEntity.ok(updateTask);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task.", description = "Return 204 response for deleted or none in case of 400.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Deleted task successfully."),
                    @ApiResponse(responseCode = "404", description = "The ID provided don't exist.")
            })
    public ResponseEntity<Void> deleteTask(@Valid @PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
