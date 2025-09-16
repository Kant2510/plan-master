package com.pm.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Entity
@Builder
@Table(name = "task_assignee")
@NoArgsConstructor
@AllArgsConstructor
public class TaskAssignee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_assignee_seq")
    @SequenceGenerator(name = "task_assignee_seq", sequenceName = "task_assignee_seq", allocationSize = 1)
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
