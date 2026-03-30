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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "EVENT_TYPE", nullable = false)
    private EventType type;

    @Column(name = "DURATION", nullable = false)
    private Integer duration;

    @Column(name = "IMAGE", nullable = false)
    private String image;

    @OneToOne(mappedBy = "parentEvent")
    private HelmetEvent childEvent;

    @OneToOne
    @JoinColumn(name = "PARENT_EVENT_ID")
    private HelmetEvent parentEvent;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "DATE_TIME",
            referencedColumnName = "DATE_TIME",
            nullable = false
    )
    private Data data;

    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    @Transient
    public LocalDateTime getDateTime() {
        return data != null ? data.getDateTime() : null;
    }


}