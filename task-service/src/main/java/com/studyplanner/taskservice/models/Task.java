package com.studyplanner.taskservice.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="task_id")
    @Schema(hidden = true)
    private long taskId;

    @NotBlank(message = "Task name is required")
    @Column(name="task_name")
    private String taskName;

    @Column(name="description")
    private String description;

    @NotBlank(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private TaskStatus status = TaskStatus.TO_DO;

    @FutureOrPresent(message = "Due date must be in the future")
    @Column(name="due_date")
    private LocalDateTime dueDate;

    @CreationTimestamp
    @Schema(hidden = true)
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Schema(hidden = true)
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="created_by")
    @Schema(hidden = true)
    private String createdBy;
}

