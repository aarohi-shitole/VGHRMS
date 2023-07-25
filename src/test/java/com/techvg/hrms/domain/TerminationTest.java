package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TerminationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Termination.class);
        Termination termination1 = new Termination();
        termination1.setId(1L);
        Termination termination2 = new Termination();
        termination2.setId(termination1.getId());
        assertThat(termination1).isEqualTo(termination2);
        termination2.setId(2L);
        assertThat(termination1).isNotEqualTo(termination2);
        termination1.setId(null);
        assertThat(termination1).isNotEqualTo(termination2);
    }
}
