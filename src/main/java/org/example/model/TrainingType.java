package org.example.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.model.enums.TrainingTypeName;

@Entity
@Data
public class TrainingType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String trainingType;

    public TrainingType() {
    }

    public TrainingType(String type) {
        this.trainingType = type;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TrainingType{");
        sb.append("id=").append(id);
        sb.append(", trainingType='").append(trainingType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
