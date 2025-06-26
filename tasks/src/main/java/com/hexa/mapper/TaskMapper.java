package com.hexa.mapper;

import com.hexa.dto.TaskDTO;
import com.hexa.model.Task;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    @Autowired
    private ModelMapper mapper;

    public Task dtoToTask(TaskDTO dto) {
        return mapper.map(dto, Task.class);
    }

    public TaskDTO taskToDto(Task task) {
        return mapper.map(task, TaskDTO.class);
    }
}
