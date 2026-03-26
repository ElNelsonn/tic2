package com.cascardo.backend.repositories;

import com.cascardo.backend.enums.EventType;
import com.cascardo.backend.models.HelmetEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HelmetEventRepository extends JpaRepository<HelmetEvent, Long> {

    Optional<HelmetEvent> findById(Long id);

    boolean existsByType(EventType eventType);

}