package com.techvg.hrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssetInventoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetInventoryDTO.class);
        AssetInventoryDTO assetInventoryDTO1 = new AssetInventoryDTO();
        assetInventoryDTO1.setId(1L);
        AssetInventoryDTO assetInventoryDTO2 = new AssetInventoryDTO();
        assertThat(assetInventoryDTO1).isNotEqualTo(assetInventoryDTO2);
        assetInventoryDTO2.setId(assetInventoryDTO1.getId());
        assertThat(assetInventoryDTO1).isEqualTo(assetInventoryDTO2);
        assetInventoryDTO2.setId(2L);
        assertThat(assetInventoryDTO1).isNotEqualTo(assetInventoryDTO2);
        assetInventoryDTO1.setId(null);
        assertThat(assetInventoryDTO1).isNotEqualTo(assetInventoryDTO2);
    }
}
