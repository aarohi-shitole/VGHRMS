package com.techvg.hrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssetApplicationMapperTest {

    private AssetApplicationMapper assetApplicationMapper;

    @BeforeEach
    public void setUp() {
        assetApplicationMapper = new AssetApplicationMapperImpl();
    }
}
