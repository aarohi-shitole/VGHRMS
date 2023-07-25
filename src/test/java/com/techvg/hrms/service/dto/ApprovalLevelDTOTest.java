package com.techvg.hrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApprovalLevelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApprovalLevelDTO.class);
        ApprovalLevelDTO approvalLevelDTO1 = new ApprovalLevelDTO();
        approvalLevelDTO1.setId(1L);
        ApprovalLevelDTO approvalLevelDTO2 = new ApprovalLevelDTO();
        assertThat(approvalLevelDTO1).isNotEqualTo(approvalLevelDTO2);
        approvalLevelDTO2.setId(approvalLevelDTO1.getId());
        assertThat(approvalLevelDTO1).isEqualTo(approvalLevelDTO2);
        approvalLevelDTO2.setId(2L);
        assertThat(approvalLevelDTO1).isNotEqualTo(approvalLevelDTO2);
        approvalLevelDTO1.setId(null);
        assertThat(approvalLevelDTO1).isNotEqualTo(approvalLevelDTO2);
    }
}
