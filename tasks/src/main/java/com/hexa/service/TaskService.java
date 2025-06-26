package com.hexa.service;

import com.hexa.dto.TaskDTO;
import com.hexa.exceptions.TaskNotFoundException;
import com.hexa.mapper.TaskMapper;
import com.hexa.model.Task;
import com.hexa.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public TaskDTO addTask(TaskDTO dto) {
        Task task = taskMapper.dtoToTask(dto); 

        if (dto.getPassword() != null) {
            String encoded = passwordEncoder.encode(dto.getPassword());
            task.setPassword(encoded); 
            System.out.println("Saving encoded password: " + encoded);
        }

        Task saved = taskRepo.save(task);
        return taskMapper.taskToDto(saved);
    }


    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepo.findAll();
        List<TaskDTO> taskDTOs = new ArrayList<>();

        for (Task task : tasks) {
            taskDTOs.add(taskMapper.taskToDto(task));
        }

        return taskDTOs;
    }

    public TaskDTO getTaskById(String taskId) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + taskId + " not found."));
        return taskMapper.taskToDto(task);
    }

    public TaskDTO updateTask(String taskId, TaskDTO dto) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + taskId + " not found."));

        if (dto.getTitle() != null) task.setTitle(dto.getTitle());
        if (dto.getDescription() != null) task.setDescription(dto.getDescription());
        if (dto.getDueDate() != null) task.setDueDate(dto.getDueDate());
        if (dto.getPriority() != null) task.setPriority(dto.getPriority());
        if (dto.getStatus() != null) task.setStatus(dto.getStatus());

        if (dto.getPassword() != null) {
            String encoded = passwordEncoder.encode(dto.getPassword());
            task.setPassword(encoded); // ✅ Correctly update with hash
        }

        Task updated = taskRepo.save(task);
        return taskMapper.taskToDto(updated);
    }


    public String deleteTask(String taskId) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + taskId + " not found."));
        taskRepo.delete(task);
        return "Task deleted successfully.";
    }
}
