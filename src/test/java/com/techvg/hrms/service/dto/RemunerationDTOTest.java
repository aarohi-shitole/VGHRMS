package com.techvg.hrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RemunerationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RemunerationDTO.class);
        RemunerationDTO remunerationDTO1 = new RemunerationDTO();
        remunerationDTO1.setId(1L);
        RemunerationDTO remunerationDTO2 = new RemunerationDTO();
        assertThat(remunerationDTO1).isNotEqualTo(remunerationDTO2);
        remunerationDTO2.setId(remunerationDTO1.getId());
        assertThat(remunerationDTO1).isEqualTo(remunerationDTO2);
        remunerationDTO2.setId(2L);
        assertThat(remunerationDTO1).isNotEqualTo(remunerationDTO2);
        remunerationDTO1.setId(null);
        assertThat(remunerationDTO1).isNotEqualTo(remunerationDTO2);
    }
}
