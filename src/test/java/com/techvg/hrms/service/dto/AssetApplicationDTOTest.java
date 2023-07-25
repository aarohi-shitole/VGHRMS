package com.techvg.hrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssetApplicationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetApplicationDTO.class);
        AssetApplicationDTO assetApplicationDTO1 = new AssetApplicationDTO();
        assetApplicationDTO1.setId(1L);
        AssetApplicationDTO assetApplicationDTO2 = new AssetApplicationDTO();
        assertThat(assetApplicationDTO1).isNotEqualTo(assetApplicationDTO2);
        assetApplicationDTO2.setId(assetApplicationDTO1.getId());
        assertThat(assetApplicationDTO1).isEqualTo(assetApplicationDTO2);
        assetApplicationDTO2.setId(2L);
        assertThat(assetApplicationDTO1).isNotEqualTo(assetApplicationDTO2);
        assetApplicationDTO1.setId(null);
        assertThat(assetApplicationDTO1).isNotEqualTo(assetApplicationDTO2);
    }
}
