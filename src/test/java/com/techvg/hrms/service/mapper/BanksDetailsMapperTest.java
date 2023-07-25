package com.techvg.hrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BanksDetailsMapperTest {

    private BanksDetailsMapper banksDetailsMapper;

    @BeforeEach
    public void setUp() {
        banksDetailsMapper = new BanksDetailsMapperImpl();
    }
}
