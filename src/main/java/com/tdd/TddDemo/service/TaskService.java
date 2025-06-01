package com.tdd.TddDemo.service;

import com.tdd.TddDemo.entity.Task;
import com.tdd.TddDemo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> get() {
        return taskRepository.findAll();
    }

    public Task getById(UUID id) throws Exception {
        return taskRepository.findById(id).orElseThrow(() -> new Exception("Not Found"));
    }

    public Task create(String text) {
        Task task = new Task(text);
        return taskRepository.save(task);
    }

    public Task update(UUID id, String text) throws Exception {
        Task task = taskRepository.findById(id).orElseThrow(() -> new Exception("Task not found"));
        task.setText(text);
        return taskRepository.save(task);
    }

    public boolean delete(UUID id) throws Exception {
        Task task = taskRepository.findById(id).orElseThrow(() -> new Exception("Task not found"));
        taskRepository.delete(task);
        return true;
    }
}
