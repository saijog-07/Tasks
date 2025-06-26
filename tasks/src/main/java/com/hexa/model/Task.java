package com.hexa.model;



import com.hexa.enums.Priority;
import com.hexa.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    private String taskId; 

    private String title;

    private String description;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String password;
}
