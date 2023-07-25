package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaySlipTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaySlip.class);
        PaySlip paySlip1 = new PaySlip();
        paySlip1.setId(1L);
        PaySlip paySlip2 = new PaySlip();
        paySlip2.setId(paySlip1.getId());
        assertThat(paySlip1).isEqualTo(paySlip2);
        paySlip2.setId(2L);
        assertThat(paySlip1).isNotEqualTo(paySlip2);
        paySlip1.setId(null);
        assertThat(paySlip1).isNotEqualTo(paySlip2);
    }
}
