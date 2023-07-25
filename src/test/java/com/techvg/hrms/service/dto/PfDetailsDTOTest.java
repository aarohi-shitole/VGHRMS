package com.techvg.hrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PfDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PfDetailsDTO.class);
        PfDetailsDTO pfDetailsDTO1 = new PfDetailsDTO();
        pfDetailsDTO1.setId(1L);
        PfDetailsDTO pfDetailsDTO2 = new PfDetailsDTO();
        assertThat(pfDetailsDTO1).isNotEqualTo(pfDetailsDTO2);
        pfDetailsDTO2.setId(pfDetailsDTO1.getId());
        assertThat(pfDetailsDTO1).isEqualTo(pfDetailsDTO2);
        pfDetailsDTO2.setId(2L);
        assertThat(pfDetailsDTO1).isNotEqualTo(pfDetailsDTO2);
        pfDetailsDTO1.setId(null);
        assertThat(pfDetailsDTO1).isNotEqualTo(pfDetailsDTO2);
    }
}
