package com.ems.controller;

import com.ems.dto.request.TaskRequestDto;
import com.ems.entity.Task;
import com.ems.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;


    // CREATE NEW TASK   ACCESS: ADMIN
    @PostMapping("/create")
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskRequestDto task) {
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }
    // UPDATE TASK   ACCESS: ADMIN
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequestDto task) {
        Task updatedTask = taskService.updateTask(id, task);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    // DELETE TASK   ACCESS: ADMIN
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(Map.of("message","Task deleted successfully"), HttpStatus.OK);
    }

    // GET TASK BY ID   ACCESS: ALL USERS
    @GetMapping("/by-id/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);

    }

    // GET ALL TASKS   ACCESS: ADMIN
    @GetMapping("/all")
    public ResponseEntity<?> getAllTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    // GET TASKS BY USERNAME   ACCESS: ALL USERS
    @GetMapping("/{username}")
    public ResponseEntity<?> getTasksByUsername(@PathVariable String username) {
        return new ResponseEntity<>(taskService.getTasksByUsername(username), HttpStatus.OK);
    }

    // UPDATE TASK STATUS   ACCESS: ALL USERS
    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updateTaskStatus(@PathVariable Long id) {
        Task updated = taskService.updateTaskCompletion(id);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}
