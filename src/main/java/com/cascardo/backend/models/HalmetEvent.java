package com.cascardo.backend.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "HALMET_EVENTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HalmetEvent {

@Id
@Column(name = "ID", unique = true, nullable = false,  updatable = false)
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

// Solo puede ser un evento
@Column(name = "EVENT_TYPE", unique = true, nullable = false)
private String eventType;

@Column(name = "DATE_TIME", nullable = false)
@CreationTimestamp
private LocalDateTime dateTime;

@OneToMany(mappedBy = "parentEvent", cascade = CascadeType.ALL)
private List<HalmetEvent> childEvents;

@Column(name = "DURATION", nullable = false)
private Integer duration;

//saco null para las pruebas y varios eventos pueden tener la misma imagen
@Column(name = "IMAGE_URL") 
private String imageUrl;

@ManyToOne
@JoinColumn(name = "PARENT_EVENT_ID")
private HalmetEvent parentEvent;

@Column(name = "CREATED_AT", nullable = false, updatable = false)
@CreationTimestamp
private LocalDateTime createdAt;

@Column(name = "UPDATED_AT", nullable = false)
@UpdateTimestamp
private LocalDateTime updatedAt;

}