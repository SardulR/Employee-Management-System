package com.ems.service;

import com.ems.entity.Performance;
import com.ems.entity.Task;
import com.ems.repository.PerformanceRepo;
import com.ems.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceService {

    @Autowired
    private PerformanceRepo performanceRepo;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepo userRepo;

    public float calculatePerformance(String username) {
        boolean existUser = userRepo.existsByUsername(username);
        if (!existUser) {
            throw new IllegalArgumentException("User does not exist");
        }
        List<Task> tasks = taskService.getTasksByUsername(username);
        if(tasks.isEmpty()) {
            return 0; // No tasks, performance is 0
        }


        List<Task> completedTasks = tasks.stream()
                .filter(Task::isCompleted)
                .toList();

        float completedPoints = completedTasks.stream()
                .mapToInt(Task::getPoints)
                .sum();

        float totalPoints = tasks.stream()
                .mapToInt(Task::getPoints)
                .sum();

        float performance = totalPoints > 0 ? (float) (completedPoints / totalPoints) * 100 : 0;
        return Math.round(performance * 100.0f) / 100.0f; // Round to 2 decimal places
    }

    public Performance savePerformance(String username){
        float performance = calculatePerformance(username);
        Performance performanceEntity = new Performance();
        performanceEntity.setUsername(username);
        performanceEntity.setPerformanceScore(performance);
        return performanceRepo.save(performanceEntity);
    }

    public Performance updatePerformance(String username, Performance performance) {
        float newScore = calculatePerformance(username);
        performance.setPerformanceScore(newScore);
        return performanceRepo.save(performance);
    }

    public List<Performance> getAllPerformances() {
        return performanceRepo.findAll();
    }

    public Performance getPerformanceByUsername(String username) {

        Performance performance = performanceRepo.findByUsername(username);
        if (performance == null) {
            return savePerformance(username);
        }
        else{
            return updatePerformance(username, performance);
        }
    }
}
