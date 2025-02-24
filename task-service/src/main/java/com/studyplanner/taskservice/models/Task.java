package com.studyplanner.taskservice.models;

import jakarta.persistence.*;
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
    private long taskId;

    @Column(name="task_name")
    private String taskName;

    @Column(name="description")
    private String description;

    @Column(name="status")
    private String status; //"To Do", "In Progress", "Done"

    @Column(name="due_date")
    private LocalDateTime dueDate;

    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="user_id")
    private long userId;
}
