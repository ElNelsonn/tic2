package com.cascardo.backend.validators;

import com.cascardo.backend.models.HalmetEvent;
import com.cascardo.backend.repositories.HalmetEventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HalmetEventValidator {

    private final HalmetEventRepository halmetEventRepository;

    public void validateEventTypeNotExists(String eventType) {
        if (halmetEventRepository.existsByEventType(eventType)) {
            throw new IllegalArgumentException("Ya existe un evento con el tipo: " + eventType);
        }
    }

    public void validateParentEventExists(Long parentEventId) {
        if (!halmetEventRepository.existsById(parentEventId)) {
            throw new IllegalArgumentException("El evento padre con ID " + parentEventId + " no existe.");
        }
    }

    
    //Valida que no haya ciclos en la cadena de eventos padres
    //Ej: A -> B -> A sería un ciclo

    public void validateNoCycles(Long parentEventId) {
        HalmetEvent currentEvent = halmetEventRepository.findById(parentEventId)
                .orElseThrow(() -> new IllegalArgumentException("El evento padre con ID " + parentEventId + " no existe."));

        while (currentEvent.getParentEvent() != null) {
            if (currentEvent.getParentEvent().getId().equals(parentEventId)) {
                throw new IllegalArgumentException("Se detectó un ciclo en la cadena de eventos padres.");
            }
            currentEvent = currentEvent.getParentEvent();
        }
    }
}