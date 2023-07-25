package com.techvg.hrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomApprovarDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomApprovarDTO.class);
        CustomApprovarDTO customApprovarDTO1 = new CustomApprovarDTO();
        customApprovarDTO1.setId(1L);
        CustomApprovarDTO customApprovarDTO2 = new CustomApprovarDTO();
        assertThat(customApprovarDTO1).isNotEqualTo(customApprovarDTO2);
        customApprovarDTO2.setId(customApprovarDTO1.getId());
        assertThat(customApprovarDTO1).isEqualTo(customApprovarDTO2);
        customApprovarDTO2.setId(2L);
        assertThat(customApprovarDTO1).isNotEqualTo(customApprovarDTO2);
        customApprovarDTO1.setId(null);
        assertThat(customApprovarDTO1).isNotEqualTo(customApprovarDTO2);
    }
}
