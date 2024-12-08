package com.workshop.todoapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    @GetMapping
    @Tag(name = "Health check controller", description = "Get the life status of the application")
    public ResponseEntity<String> getAppStatus() {
        return ResponseEntity.ok("Application is running ok.");
    }
}
