package com.techvg.hrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TdsMapperTest {

    private TdsMapper tdsMapper;

    @BeforeEach
    public void setUp() {
        tdsMapper = new TdsMapperImpl();
    }
}
