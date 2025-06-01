package com.tdd.TddDemo.controller;

import com.tdd.TddDemo.entity.Task;
import com.tdd.TddDemo.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Test
    void shouldReturnAllTasks() throws Exception {
        List<Task> tasks = List.of(new Task("Task 1"), new Task("Task 2"));
        Mockito.when(taskService.get()).thenReturn(tasks);

        mockMvc.perform(get("/api/task"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].text").value("Task 1"));
    }

    @Test
    void shouldReturnTaskById() throws Exception {
        UUID id = UUID.randomUUID();
        Task task = new Task("Sample task");
        Mockito.when(taskService.getById(id)).thenReturn(task);

        mockMvc.perform(get("/api/task/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Sample task"));
    }

    @Test
    void shouldCreateTask() throws Exception {
        Task task = new Task("New task");
        Mockito.when(taskService.create("New task")).thenReturn(task);

        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"New task\""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("New task"));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        UUID id = UUID.randomUUID();
        Task task = new Task("Updated task");
        task.setId(id);

        Mockito.when(taskService.update(id, "Updated task")).thenReturn(task);

        mockMvc.perform(put("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"" + id + "\", \"text\":\"Updated task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Updated task"));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(taskService.delete(id)).thenReturn(true);

        mockMvc.perform(delete("/api/task/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}

