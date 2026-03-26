package com.cascardo.backend.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "METRICS",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_METRICS_DATE_TIME", columnNames = "DATE_TIME")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Metrics {

    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DATE_TIME", nullable = false, unique = true)
    private LocalDateTime dateTime;

    @Column(name = "TEMPERATURE", nullable = false)
    private Float temperature;

    @Column(name = "HUMIDITY", nullable = false)
    private Float humidity;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "metric")
    private List<HelmetEvent> events = new ArrayList<>();
}