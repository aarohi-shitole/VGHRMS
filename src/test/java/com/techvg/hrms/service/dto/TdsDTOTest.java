package com.techvg.hrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TdsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TdsDTO.class);
        TdsDTO tdsDTO1 = new TdsDTO();
        tdsDTO1.setId(1L);
        TdsDTO tdsDTO2 = new TdsDTO();
        assertThat(tdsDTO1).isNotEqualTo(tdsDTO2);
        tdsDTO2.setId(tdsDTO1.getId());
        assertThat(tdsDTO1).isEqualTo(tdsDTO2);
        tdsDTO2.setId(2L);
        assertThat(tdsDTO1).isNotEqualTo(tdsDTO2);
        tdsDTO1.setId(null);
        assertThat(tdsDTO1).isNotEqualTo(tdsDTO2);
    }
}
