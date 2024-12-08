package com.workshop.todoapp.controller.global_exception_handler;

import com.workshop.todoapp.exceptions.TaskNotFoundException;
import com.workshop.todoapp.exceptions.TasksNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException exceptions){
        Map<String , String> clientErrors = new HashMap<>();

        exceptions.getBindingResult().getFieldErrors().forEach(error -> {
            clientErrors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(clientErrors);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeExceptions(RuntimeException exceptions){
        Map<String, String> runtimeErrors = new HashMap<>();

        runtimeErrors.put("error", exceptions.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(runtimeErrors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericExceptions(Exception exceptions){
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("error", "Something went wrong!");
        errorResponse.put("details", exceptions.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleTaskNotFoundException(TaskNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(TasksNotFoundException.class)
    public ResponseEntity<String> handleTaskNotFoundException(TasksNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
