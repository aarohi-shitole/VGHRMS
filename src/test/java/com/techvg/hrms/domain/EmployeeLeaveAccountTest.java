package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeLeaveAccountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeLeaveAccount.class);
        EmployeeLeaveAccount employeeLeaveAccount1 = new EmployeeLeaveAccount();
        employeeLeaveAccount1.setId(1L);
        EmployeeLeaveAccount employeeLeaveAccount2 = new EmployeeLeaveAccount();
        employeeLeaveAccount2.setId(employeeLeaveAccount1.getId());
        assertThat(employeeLeaveAccount1).isEqualTo(employeeLeaveAccount2);
        employeeLeaveAccount2.setId(2L);
        assertThat(employeeLeaveAccount1).isNotEqualTo(employeeLeaveAccount2);
        employeeLeaveAccount1.setId(null);
        assertThat(employeeLeaveAccount1).isNotEqualTo(employeeLeaveAccount2);
    }
}
