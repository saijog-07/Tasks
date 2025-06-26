package com.hexa.dto;

import com.hexa.enums.Priority;
import com.hexa.enums.Status;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class TaskDTO {

    @Pattern(regexp = "\\d{6}", message = "Task ID must be exactly 6 digits")
    private String taskId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    @NotNull(message = "Priority is required")
    private Priority priority;

    @NotNull(message = "Status is required")
    private Status status;

    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}
