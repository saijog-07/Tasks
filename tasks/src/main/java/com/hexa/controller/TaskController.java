package com.hexa.controller;

import com.hexa.dto.TaskDTO;
import com.hexa.service.*;
import com.hexa.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskDetailsServiceImpl taskDetails;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public TaskController(TaskService taskService, TaskDetailsServiceImpl taskDetails,
                          AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.taskService = taskService;
        this.taskDetails = taskDetails;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<TaskDTO> register(@RequestBody @Valid TaskDTO taskDTO) {
        TaskDTO created = taskService.addTask(taskDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Header-info", "Task registered successfully");
        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody TaskDTO taskDTO) {
        try {
            System.out.println("Trying login with -> taskId: " + taskDTO.getTaskId() + ", password: " + taskDTO.getPassword());

            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(taskDTO.getTaskId(), taskDTO.getPassword())
            );

            UserDetails userDetails = taskDetails.loadUserByUsername(taskDTO.getTaskId());
            String token = jwtUtil.generateToken(userDetails.getUsername());

            return ResponseEntity.ok(token);

        } catch (Exception e) {
            e.printStackTrace();  
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("Login failed: " + e.getMessage());
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable String taskId) {
        TaskDTO task = taskService.getTaskById(taskId);
        return ResponseEntity.ok().body(task);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable String taskId, @RequestBody TaskDTO dto) {
        TaskDTO updated = taskService.updateTask(taskId, dto);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable String taskId) {
        String message = taskService.deleteTask(taskId);
        return ResponseEntity.ok(message);
    }
}
