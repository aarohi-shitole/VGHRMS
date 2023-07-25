package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssetInventoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetInventory.class);
        AssetInventory assetInventory1 = new AssetInventory();
        assetInventory1.setId(1L);
        AssetInventory assetInventory2 = new AssetInventory();
        assetInventory2.setId(assetInventory1.getId());
        assertThat(assetInventory1).isEqualTo(assetInventory2);
        assetInventory2.setId(2L);
        assertThat(assetInventory1).isNotEqualTo(assetInventory2);
        assetInventory1.setId(null);
        assertThat(assetInventory1).isNotEqualTo(assetInventory2);
    }
}
