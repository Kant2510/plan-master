package com.pm.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.pm.backend.constant.SprintConstant;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@Table(name = "sprint", indexes = @Index(columnList = "project_id, orderIndex", unique = true))
@NoArgsConstructor
@AllArgsConstructor
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sprint_seq")
    @SequenceGenerator(name = "sprint_seq", sequenceName = "sprint_seq", allocationSize = 1)
    private Integer id;

    @NotNull
    private Integer orderIndex;

    @Column(name = "user_created")
    private UUID user_created;

    @Column(name = "user_updated")
    private UUID user_updated;

    @Column(name = "date_created")
    @CreationTimestamp
    private Instant date_created;

    @Column(name = "date_updated")
    @UpdateTimestamp
    private Instant date_updated;

    @NotNull
    @Column(name = "start_date")
    private Instant start_date;

    @NotNull
    @Column(name = "end_date")
    private Instant end_date;

    @Enumerated(EnumType.STRING)
    private SprintConstant.SPRINT_STATUS status; // PLANNED/ACTIVE/DONE

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @NotNull
    private Project project;

    @OneToOne(mappedBy = "sprint", cascade = CascadeType.ALL, orphanRemoval = true)
    private Board board;
}
