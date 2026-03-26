package com.cascardo.backend.models;

import com.cascardo.backend.enums.EventType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;




@Entity
@Table(name = "HELMET_EVENTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HelmetEvent {

    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "EVENT_TYPE", nullable = false)
    private EventType type;

    @Column(name = "DATE_TIME", nullable = false)
    private LocalDateTime dateTime;

    @OneToOne(mappedBy = "parentEvent")
    private HelmetEvent childEvent;

    @Column(name = "DURATION", nullable = false)
    private Integer duration;

    @Column(name = "IMAGE_URL", nullable = false)
    private String imageUrl;

    @OneToOne
    @JoinColumn(name = "PARENT_EVENT_ID")
    private HelmetEvent parentEvent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "METRIC_DATE_TIME",
            referencedColumnName = "DATE_TIME",
            nullable = false
    )
    private Metrics metric;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}