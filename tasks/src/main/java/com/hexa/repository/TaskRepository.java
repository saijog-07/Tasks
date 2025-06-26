package com.hexa.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hexa.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, String>{

	Task findByTaskId(String taskId);

}
