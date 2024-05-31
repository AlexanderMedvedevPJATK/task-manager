package com.amedvedev.taskmanager.controller;

import com.amedvedev.taskmanager.service.TaskService;
import com.amedvedev.taskmanager.entities.Task;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String tasks(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks";
    }

    @DeleteMapping("/tasks/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        try {
            taskService.delete(id);
            return ResponseEntity.ok("Record deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "Failed to delete record with id " + id + ": " + e.getMessage()
            );
        }
    }

    @GetMapping("/clear")
    public RedirectView clear() {
        taskService.deleteAll();
        return new RedirectView("/tasks", true, false);
    }

    @PostMapping("/tasks")
    @ResponseBody
    public ResponseEntity<?> saveTask(@Valid @RequestBody Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        taskService.save(task);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @GetMapping("/tasks/{id}")
    @ResponseBody
    public Task findTaskById(@PathVariable Long id) {
        return taskService.find(id);
    }
}
