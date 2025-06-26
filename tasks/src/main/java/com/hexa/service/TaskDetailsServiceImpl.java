package com.hexa.service;

import com.hexa.model.Task;
import com.hexa.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class TaskDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private TaskRepository taskRepo;

    @Override
    public UserDetails loadUserByUsername(String taskId) throws UsernameNotFoundException {
        Task task = taskRepo.findByTaskId(taskId);
        if (task == null) {
            throw new UsernameNotFoundException("Task not found: " + taskId);
        }

        System.out.println("Loaded user from DB -> TaskId: " + task.getTaskId() + ", Password: " + task.getPassword());

        return new User(task.getTaskId(), task.getPassword(), Collections.emptyList());
    }
}
