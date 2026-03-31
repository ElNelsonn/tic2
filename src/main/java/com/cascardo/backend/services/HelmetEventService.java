package com.cascardo.backend.services;

import com.cascardo.backend.dto.CreateHelmetEventDto;
import com.cascardo.backend.exceptions.HelmetEventNotFoundException;
import com.cascardo.backend.mappers.HelmetEventMapper;
import com.cascardo.backend.minio.MinioService;
import com.cascardo.backend.models.Data;
import com.cascardo.backend.models.HelmetEvent;
import com.cascardo.backend.repositories.HelmetEventRepository;
import com.cascardo.backend.validators.HelmetEventValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;


@Service
@AllArgsConstructor
public class HelmetEventService {

    private final HelmetEventRepository helmetEventRepository;
    private final HelmetEventValidator helmetEventValidator;
    private final MinioService minioService;
    private final DataService dataService;

    @Transactional
    public HelmetEvent createHelmetEvent(CreateHelmetEventDto createHelmetEventDto, MultipartFile photo) {

        if (photo == null || photo.isEmpty()) {
            throw new IllegalArgumentException("La foto es obligatoria.");
        }

        HelmetEvent newEvent = HelmetEventMapper.toEntity(createHelmetEventDto);
        Data eventData = dataService.saveData(createHelmetEventDto.addDataDto());

        newEvent.setData(eventData);


        if (createHelmetEventDto.parentEventId() != null) {
            setParentEvent(newEvent, createHelmetEventDto.parentEventId());
        }

        newEvent = helmetEventRepository.save(newEvent);

        String objectName = minioService.saveEventPhoto(newEvent.getId(), photo);

        registerMinioRollbackCleanup(objectName);

        newEvent.setImage(objectName);

        return helmetEventRepository.save(newEvent);
    }

    private void registerMinioRollbackCleanup(String objectName) {

        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            return;
        }

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if (status == STATUS_ROLLED_BACK) {
                    try {
                        minioService.deleteEventPhoto(objectName);
                    } catch (Exception e) {
                        System.err.println("Error al eliminar la foto de Minio tras rollback: " + e.getMessage());
                    }
                }
            }
        });
    }

    @Transactional(readOnly = true)
    protected HelmetEvent findById(Long id) {

        return helmetEventRepository.findById(id)
                .orElseThrow(() -> new HelmetEventNotFoundException("El evento no existe."));
    }

    private void setParentEvent(HelmetEvent childEvent, Long parentId) {

        HelmetEvent parentEvent = findById(parentId);

        helmetEventValidator.validateParentCanBeAssigned(parentEvent);

        childEvent.setParentEvent(parentEvent);

    }





}
