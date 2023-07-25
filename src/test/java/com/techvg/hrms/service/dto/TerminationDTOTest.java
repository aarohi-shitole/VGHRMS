package com.techvg.hrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TerminationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TerminationDTO.class);
        TerminationDTO terminationDTO1 = new TerminationDTO();
        terminationDTO1.setId(1L);
        TerminationDTO terminationDTO2 = new TerminationDTO();
        assertThat(terminationDTO1).isNotEqualTo(terminationDTO2);
        terminationDTO2.setId(terminationDTO1.getId());
        assertThat(terminationDTO1).isEqualTo(terminationDTO2);
        terminationDTO2.setId(2L);
        assertThat(terminationDTO1).isNotEqualTo(terminationDTO2);
        terminationDTO1.setId(null);
        assertThat(terminationDTO1).isNotEqualTo(terminationDTO2);
    }
}
