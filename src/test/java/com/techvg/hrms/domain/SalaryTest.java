package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SalaryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Salary.class);
        Salary salary1 = new Salary();
        salary1.setId(1L);
        Salary salary2 = new Salary();
        salary2.setId(salary1.getId());
        assertThat(salary1).isEqualTo(salary2);
        salary2.setId(2L);
        assertThat(salary1).isNotEqualTo(salary2);
        salary1.setId(null);
        assertThat(salary1).isNotEqualTo(salary2);
    }
}
