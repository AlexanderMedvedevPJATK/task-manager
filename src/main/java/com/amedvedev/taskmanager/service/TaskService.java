package com.amedvedev.taskmanager.service;

import com.amedvedev.taskmanager.task.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskService {
    TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task find(Long id) {
        return taskRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public void save(Task task) {
        taskRepository.save(task);
    }

    public void deleteAll() {
        taskRepository.deleteAll();
    }

    public void delete(Long id) {
        taskRepository.delete(find(id));
    }
}
