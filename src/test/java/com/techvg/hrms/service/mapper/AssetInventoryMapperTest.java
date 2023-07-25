package com.techvg.hrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssetInventoryMapperTest {

    private AssetInventoryMapper assetInventoryMapper;

    @BeforeEach
    public void setUp() {
        assetInventoryMapper = new AssetInventoryMapperImpl();
    }
}
