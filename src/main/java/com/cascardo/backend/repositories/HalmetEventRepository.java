package com.cascardo.backend.repositories;

import com.cascardo.backend.models.HalmetEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HalmetEventRepository extends JpaRepository<HalmetEvent, Long> {

    Optional<HalmetEvent> findById(Long id);

    boolean existsByEventType(String eventType);

}