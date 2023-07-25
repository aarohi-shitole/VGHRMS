package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssetApplicationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetApplication.class);
        AssetApplication assetApplication1 = new AssetApplication();
        assetApplication1.setId(1L);
        AssetApplication assetApplication2 = new AssetApplication();
        assetApplication2.setId(assetApplication1.getId());
        assertThat(assetApplication1).isEqualTo(assetApplication2);
        assetApplication2.setId(2L);
        assertThat(assetApplication1).isNotEqualTo(assetApplication2);
        assetApplication1.setId(null);
        assertThat(assetApplication1).isNotEqualTo(assetApplication2);
    }
}
