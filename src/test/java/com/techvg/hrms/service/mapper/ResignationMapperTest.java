package com.techvg.hrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResignationMapperTest {

    private ResignationMapper resignationMapper;

    @BeforeEach
    public void setUp() {
        resignationMapper = new ResignationMapperImpl();
    }
}
