package com.cascardo.backend.validators;

import com.cascardo.backend.enums.EventType;
import com.cascardo.backend.models.HelmetEvent;
import com.cascardo.backend.repositories.HelmetEventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelmetEventValidatorTest {

    @Mock private HelmetEventRepository helmetEventRepository;
    @InjectMocks private HelmetEventValidator helmetEventValidator;

    @Test
    void validateEventTypeNotExists_whenExists_throws() {
        when(helmetEventRepository.existsByType(EventType.WARNING)).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> helmetEventValidator.validateEventTypeNotExists(EventType.WARNING));
    }

    @Test
    void validateParentCanBeAssigned_whenParentIsChild_throws() {
        HelmetEvent parentParent = HelmetEvent.builder().id(1L).build();
        HelmetEvent parent = HelmetEvent.builder().id(2L).parentEvent(parentParent).build();

        assertThrows(IllegalArgumentException.class, () -> helmetEventValidator.validateParentCanBeAssigned(parent));
    }

    @Test
    void validateParentCanBeAssigned_whenAlreadyHasChild_throws() {
        HelmetEvent child = HelmetEvent.builder().id(3L).build();
        HelmetEvent parent = HelmetEvent.builder().id(2L).childEvent(child).build();

        assertThrows(IllegalArgumentException.class, () -> helmetEventValidator.validateParentCanBeAssigned(parent));
    }
}