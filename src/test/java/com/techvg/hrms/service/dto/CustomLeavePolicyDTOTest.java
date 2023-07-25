package com.techvg.hrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomLeavePolicyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomLeavePolicyDTO.class);
        CustomLeavePolicyDTO customLeavePolicyDTO1 = new CustomLeavePolicyDTO();
        customLeavePolicyDTO1.setId(1L);
        CustomLeavePolicyDTO customLeavePolicyDTO2 = new CustomLeavePolicyDTO();
        assertThat(customLeavePolicyDTO1).isNotEqualTo(customLeavePolicyDTO2);
        customLeavePolicyDTO2.setId(customLeavePolicyDTO1.getId());
        assertThat(customLeavePolicyDTO1).isEqualTo(customLeavePolicyDTO2);
        customLeavePolicyDTO2.setId(2L);
        assertThat(customLeavePolicyDTO1).isNotEqualTo(customLeavePolicyDTO2);
        customLeavePolicyDTO1.setId(null);
        assertThat(customLeavePolicyDTO1).isNotEqualTo(customLeavePolicyDTO2);
    }
}
