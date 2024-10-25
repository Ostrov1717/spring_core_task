package org.example.services;

import jakarta.annotation.PostConstruct;
import org.example.dao.TrainingTypeRepository;
import org.example.model.TrainingType;
import org.example.model.enums.TrainingTypeName;
import org.springframework.stereotype.Service;

@Service
public class TrainingTypeInitializer {
    private final TrainingTypeRepository trainingTypeRepository;

    public TrainingTypeInitializer(TrainingTypeRepository trainingTypeRepository) {
        this.trainingTypeRepository = trainingTypeRepository;
    }

    @PostConstruct
    public void init() {
        for (TrainingTypeName type : TrainingTypeName.values()) {
            TrainingType entity = new TrainingType();
            entity.setTrainingType(type.name());
            trainingTypeRepository.save(entity);
        }
    }
}
