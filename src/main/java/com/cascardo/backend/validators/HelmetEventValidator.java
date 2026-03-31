package com.cascardo.backend.validators;

import com.cascardo.backend.enums.EventType;
import com.cascardo.backend.models.HelmetEvent;
import com.cascardo.backend.repositories.HelmetEventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HelmetEventValidator {

    private final HelmetEventRepository helmetEventRepository;

    public void validateEventTypeNotExists(EventType eventType) {

        if (helmetEventRepository.existsByType(eventType)) {
            throw new IllegalArgumentException("Ya existe un evento con el tipo: " + eventType);
        }
    }

    public void validateParentCanBeAssigned(HelmetEvent parent) {

        // Regla 1: el padre no puede ser hijo de otro
        if (parent.getParentEvent() != null) {
            throw new IllegalArgumentException(
                    "El evento " + parent.getId() + " no puede ser padre porque ya es hijo de otro evento."
            );
        }

        // Regla 2: el padre no puede tener ya un hijo
        if (parent.getChildEvent() != null) {
            throw new IllegalArgumentException(
                    "El evento " + parent.getId() + " no puede ser padre porque ya tiene un hijo asignado."
            );
        }
    }
}