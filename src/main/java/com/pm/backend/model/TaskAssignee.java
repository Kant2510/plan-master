package com.pm.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.Instant;

@Getter
@Entity
@Table(name = "task_assignee")
public class TaskAssignee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    @NotNull
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private UserProfile member;

    //@Enumerated(EnumType.STRING)
    private String roleOnTask; // OWNER/REVIEWER/DEV...

    private Instant assignedAt;
}
