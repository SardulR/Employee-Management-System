package com.ems.service;

import com.ems.dto.request.TaskRequestDto;
import com.ems.entity.Task;
import com.ems.enums.TaskPriority;
import com.ems.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {


    @Autowired
    private TaskRepo taskRepo;

    public Task createTask(TaskRequestDto task) {
        int point = 5; // Default points for non-urgent tasks
        if(task.getPriority()!=null && task.getPriority() == TaskPriority.URGENT){
            point = 10;
        }
        Task newTask = new Task().builder()
                .taskName(task.getTaskName())
                .description(task.getDescription())
                .username(task.getUsername())
                .isCompleted(false) // Default value for new tasks
                .priority(task.getPriority())
                .points(point)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
        return taskRepo.save(newTask);
    }


    public Task updateTask(Long id, TaskRequestDto task) {
        Task existingTask = taskRepo.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        existingTask.setTaskName(task.getTaskName());
        existingTask.setDescription(task.getDescription());
        existingTask.setUsername(task.getUsername());
        existingTask.setPriority(task.getPriority());
        existingTask.setPoints(task.getPriority().equals(TaskPriority.URGENT) ? 10 : 5);
        existingTask.setUpdatedDate(LocalDateTime.now());
        return taskRepo.save(existingTask);
    }

    public void deleteTask(Long id) {
        Task existingTask = taskRepo.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepo.delete(existingTask);
    }

    public Task getTaskById(Long id) {
        return taskRepo.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    public List<Task> getTasksByUsername(String username) {
        return taskRepo.findByUsername(username);
    }

    public Task updateTaskCompletion(Long id) {
        Task existingTask = taskRepo.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        existingTask.setCompleted(true);
        existingTask.setUpdatedDate(LocalDateTime.now());
        return taskRepo.save(existingTask);
    }
}
