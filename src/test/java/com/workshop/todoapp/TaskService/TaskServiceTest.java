package com.workshop.todoapp.TaskService;

import com.workshop.todoapp.exceptions.TaskNotFoundException;
import com.workshop.todoapp.exceptions.TasksNotFoundException;
import com.workshop.todoapp.model.Task;
import com.workshop.todoapp.repository.TaskRepository;
import com.workshop.todoapp.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskService taskService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllTasksTestException(){
        when(taskRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(TasksNotFoundException.class, () -> taskService.getAllTasks(), "Test exception for get all tasks executed successfully.");
    }

    @Test
    void getAllTasksTest(){
        when(taskRepository.findAll()).thenReturn(Arrays.asList(new Task(), new Task()));
        List<Task> tasks = taskService.getAllTasks();
        assertEquals(2, tasks.size(), "Test for getting all tasks passed successfully.");
    }

    @Test
    void getTaskByIdTestException(){
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService
                .getTaskByID(taskId), "Test exception for get task by ID executed successfully.");
    }

    @Test
    void getTaskByIdTest(){
        Long taskId = 1L;
        Task mockTask = new Task();
        mockTask.setId(taskId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(mockTask));
        Task task = taskService.getTaskByID(taskId).get();
        assertEquals(taskId, task.getId(), "Test for getting task by ID passed successfully.");
    }

    @Test
    void updateTaskTestException(){
        Long taskId = 1L;
        Task taskDetails = new Task();
        taskDetails.setTitle("Test Title");
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(new Task()));
        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(taskId, taskDetails), "Test exception for updating task passed successfully.");
    }

    @Test
    void updateTaskTest(){
        Long taskId = 1L;

        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setTitle("Existing Title");

        Task updatedTaskDetails = new Task();
        updatedTaskDetails.setTitle("Updated Title");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task savedTask = invocation.getArgument(0);
            savedTask.setId(taskId);
            return savedTask;
        });

        Task updatedTask = taskService.updateTask(taskId, updatedTaskDetails);
        assertEquals(updatedTaskDetails.getTitle(), updatedTask.getTitle(), "Test for updating task passed successfully.");
    }

    @Test
    void deleteTaskTestException() {
        Long taskId = 1L;
        when(taskRepository.existsById(taskId)).thenReturn(false);
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(taskId), "Test exception for deleting task passed successfully.");
    }

    @Test
    void deleteTaskTest() {
        Long taskId = 1L;
        when(taskRepository.existsById(taskId)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(taskId);
        taskService.deleteTask(taskId);
        verify(taskRepository, times(1)).deleteById(taskId);
    }
}

