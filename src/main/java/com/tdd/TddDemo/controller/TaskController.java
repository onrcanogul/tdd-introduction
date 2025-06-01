package com.tdd.TddDemo.controller;

import com.tdd.TddDemo.entity.Task;
import com.tdd.TddDemo.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {

        this.taskService = taskService;
    }


    @GetMapping
    public ResponseEntity<List<Task>> get() {
        return ResponseEntity.ok(taskService.get());
    }

    @GetMapping("{id}")
    public ResponseEntity<Task> getById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(taskService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.create(task.getText()));
    }

    @PutMapping
    public ResponseEntity<Task> update(@RequestBody Task task) throws Exception {
        return ResponseEntity.ok(taskService.update(task.getId(), task.getText()));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(taskService.delete(id));
    }
}
