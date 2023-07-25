package com.techvg.hrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TerminationMapperTest {

    private TerminationMapper terminationMapper;

    @BeforeEach
    public void setUp() {
        terminationMapper = new TerminationMapperImpl();
    }
}
