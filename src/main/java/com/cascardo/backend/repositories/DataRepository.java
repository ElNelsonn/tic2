package com.cascardo.backend.repositories;

import com.cascardo.backend.models.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface DataRepository extends JpaRepository<Data, Long> {


    Optional<Data> findById(Long id);

    Optional<Data> findByDateTime(LocalDateTime dateTime);

    boolean existsByDateTime(LocalDateTime dateTime);


}
