package com.tdd.TddDemo.service;

import com.tdd.TddDemo.entity.Task;
import com.tdd.TddDemo.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    public void shouldReturnAllTasks_whenTasksExist() {
        List<Task> tasks = List.of(new Task("Task1"), new Task("Task2"));
        Mockito.when(taskRepository.findAll()).thenReturn(tasks);
        List<Task> result = taskService.get();
        assertEquals(tasks.size(), result.size());
        assertEquals("Task1", result.getFirst().getText());
    }

    @Test
    public void shouldReturnTask_whenTaskExistsById() throws Exception {
        UUID id = UUID.randomUUID();
        Task task = new Task("TestTask");
        Mockito.when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        Task result = taskService.getById(id);
        assertEquals("TestTask", result.getText());
    }

    @Test
    public void shouldThrowException_whenTaskNotFoundById() {
        UUID id = UUID.randomUUID();
        Mockito.when(taskRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> taskService.getById(id));
    }

    @Test
    public void shouldCreateTask_whenValidInputProvided() {
        String text = "Learn Tdd";
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(new Task(text));
        Task createdTask = taskService.create(text);
        assertNotNull(createdTask.getId());
        assertEquals(text, createdTask.getText());
    }

    @Test
    public void shouldUpdateTask_whenTaskExists() throws Exception {
        UUID id = UUID.randomUUID();
        Task existingTask = new Task("Old Text");
        Mockito.when(taskRepository.findById(id)).thenReturn(Optional.of(existingTask));
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(new Task("Learn Tdd"));
        Task updatedTask = taskService.update(id, "Learn Tdd");
        assertNotNull(updatedTask.getId());
        assertEquals("Learn Tdd", updatedTask.getText());
    }

    @Test
    public void shouldThrowException_whenTaskNotFoundForUpdate() {
        UUID id = UUID.randomUUID();
        Mockito.when(taskRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> taskService.update(id, "Learn Tdd"));
    }

    @Test
    public void shouldThrowException_whenTaskNotFoundForDelete() {
        UUID id = UUID.randomUUID();
        Mockito.when(taskRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> taskService.delete(id));
    }

    @Test
    public void shouldDeleteTask_whenTaskExists() throws Exception {
        UUID id = UUID.randomUUID();
        Task task = new Task("Learn Tdd");
        Mockito.when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        boolean result = taskService.delete(id);
        assertTrue(result);
        Mockito.verify(taskRepository).delete(task);
    }
}
