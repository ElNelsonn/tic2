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

    public void validateParentEventExists(Long parentEventId) {
        if (!helmetEventRepository.existsById(parentEventId)) {
            throw new IllegalArgumentException("El evento padre con ID " + parentEventId + " no existe.");
        }
    }

    
    //Valida que no haya ciclos en la cadena de eventos padres
    //Ej: A -> B -> A sería un ciclo

    public void validateNoCycles(Long parentEventId) {

        HelmetEvent currentEvent = helmetEventRepository.findById(parentEventId)
                .orElseThrow(() -> new IllegalArgumentException("El evento padre con ID " + parentEventId + " no existe."));

        while (currentEvent.getParentEvent() != null) {
            if (currentEvent.getParentEvent().getId().equals(parentEventId)) {
                throw new IllegalArgumentException("Se detectó un ciclo en la cadena de eventos padres.");
            }
            currentEvent = currentEvent.getParentEvent();
        }
    }
}