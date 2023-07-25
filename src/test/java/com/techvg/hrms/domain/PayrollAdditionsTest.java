package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PayrollAdditionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayrollAdditions.class);
        PayrollAdditions payrollAdditions1 = new PayrollAdditions();
        payrollAdditions1.setId(1L);
        PayrollAdditions payrollAdditions2 = new PayrollAdditions();
        payrollAdditions2.setId(payrollAdditions1.getId());
        assertThat(payrollAdditions1).isEqualTo(payrollAdditions2);
        payrollAdditions2.setId(2L);
        assertThat(payrollAdditions1).isNotEqualTo(payrollAdditions2);
        payrollAdditions1.setId(null);
        assertThat(payrollAdditions1).isNotEqualTo(payrollAdditions2);
    }
}
