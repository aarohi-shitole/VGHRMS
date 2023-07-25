package com.techvg.hrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeLeaveAccountDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeLeaveAccountDTO.class);
        EmployeeLeaveAccountDTO employeeLeaveAccountDTO1 = new EmployeeLeaveAccountDTO();
        employeeLeaveAccountDTO1.setId(1L);
        EmployeeLeaveAccountDTO employeeLeaveAccountDTO2 = new EmployeeLeaveAccountDTO();
        assertThat(employeeLeaveAccountDTO1).isNotEqualTo(employeeLeaveAccountDTO2);
        employeeLeaveAccountDTO2.setId(employeeLeaveAccountDTO1.getId());
        assertThat(employeeLeaveAccountDTO1).isEqualTo(employeeLeaveAccountDTO2);
        employeeLeaveAccountDTO2.setId(2L);
        assertThat(employeeLeaveAccountDTO1).isNotEqualTo(employeeLeaveAccountDTO2);
        employeeLeaveAccountDTO1.setId(null);
        assertThat(employeeLeaveAccountDTO1).isNotEqualTo(employeeLeaveAccountDTO2);
    }
}
