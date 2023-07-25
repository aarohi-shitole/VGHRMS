package com.techvg.hrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainingTypeMapperTest {

    private TrainingTypeMapper trainingTypeMapper;

    @BeforeEach
    public void setUp() {
        trainingTypeMapper = new TrainingTypeMapperImpl();
    }
}
