package org.example.model;

import jakarta.persistence.AttributeConverter;

import java.time.Duration;

public class DurationConverter implements AttributeConverter<Duration,Long> {

    @Override
    public Long convertToDatabaseColumn(Duration duration) {
        return duration != null ? duration.getSeconds() : null;
    }

    @Override
    public Duration convertToEntityAttribute(Long seconds) {
        return seconds != null ? Duration.ofSeconds(seconds) : null;
    }

}
