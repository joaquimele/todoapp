package com.workshop.todoapp.exceptions;

public class TasksNotFoundException extends RuntimeException {
    public TasksNotFoundException(String message) {
        super(message);
    }
}
