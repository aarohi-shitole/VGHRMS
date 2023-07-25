package com.techvg.hrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PayrollAdditionsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayrollAdditionsDTO.class);
        PayrollAdditionsDTO payrollAdditionsDTO1 = new PayrollAdditionsDTO();
        payrollAdditionsDTO1.setId(1L);
        PayrollAdditionsDTO payrollAdditionsDTO2 = new PayrollAdditionsDTO();
        assertThat(payrollAdditionsDTO1).isNotEqualTo(payrollAdditionsDTO2);
        payrollAdditionsDTO2.setId(payrollAdditionsDTO1.getId());
        assertThat(payrollAdditionsDTO1).isEqualTo(payrollAdditionsDTO2);
        payrollAdditionsDTO2.setId(2L);
        assertThat(payrollAdditionsDTO1).isNotEqualTo(payrollAdditionsDTO2);
        payrollAdditionsDTO1.setId(null);
        assertThat(payrollAdditionsDTO1).isNotEqualTo(payrollAdditionsDTO2);
    }
}
