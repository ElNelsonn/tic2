package com.cascardo.backend.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "METRICS")
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

    //ver si podes tener un evento sin metricas.
    @ManyToOne 
    @JoinColumn(name = "HALMET_EVENT_ID", nullable = false)
    private HalmetEvent halmetEvent;

    @Column(name = "DATE_TIME", nullable = false)
    @CreationTimestamp
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
}