package com.workshop.todoapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Title cannot be null")
    @Size(min = 1, message = "Title must have at least 1 character")
    private String title;

    @NotNull(message = "Description cannot be null")
    @Size(min = 1, message = "Description must have at least 1 character")
    private String description;

    @NotNull(message = "Completion status cannot be null")
    private Boolean completed;

    public Task(long id, String title, String description, Boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
    }
}
