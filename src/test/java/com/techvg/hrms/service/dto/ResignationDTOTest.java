package com.techvg.hrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResignationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResignationDTO.class);
        ResignationDTO resignationDTO1 = new ResignationDTO();
        resignationDTO1.setId(1L);
        ResignationDTO resignationDTO2 = new ResignationDTO();
        assertThat(resignationDTO1).isNotEqualTo(resignationDTO2);
        resignationDTO2.setId(resignationDTO1.getId());
        assertThat(resignationDTO1).isEqualTo(resignationDTO2);
        resignationDTO2.setId(2L);
        assertThat(resignationDTO1).isNotEqualTo(resignationDTO2);
        resignationDTO1.setId(null);
        assertThat(resignationDTO1).isNotEqualTo(resignationDTO2);
    }
}
