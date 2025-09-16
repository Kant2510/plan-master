package com.pm.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "phase", indexes = @Index(columnList = "board_id, orderIndex", unique = true))
@NoArgsConstructor
@AllArgsConstructor
public class Phase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phase_seq")
    @SequenceGenerator(name = "phase_seq", sequenceName = "phase_seq", allocationSize = 1)
    private Integer id;

    @NotNull
    private String title;

    @NotNull
    private Integer orderIndex;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();
}
