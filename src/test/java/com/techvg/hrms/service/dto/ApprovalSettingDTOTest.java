package com.techvg.hrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApprovalSettingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApprovalSettingDTO.class);
        ApprovalSettingDTO approvalSettingDTO1 = new ApprovalSettingDTO();
        approvalSettingDTO1.setId(1L);
        ApprovalSettingDTO approvalSettingDTO2 = new ApprovalSettingDTO();
        assertThat(approvalSettingDTO1).isNotEqualTo(approvalSettingDTO2);
        approvalSettingDTO2.setId(approvalSettingDTO1.getId());
        assertThat(approvalSettingDTO1).isEqualTo(approvalSettingDTO2);
        approvalSettingDTO2.setId(2L);
        assertThat(approvalSettingDTO1).isNotEqualTo(approvalSettingDTO2);
        approvalSettingDTO1.setId(null);
        assertThat(approvalSettingDTO1).isNotEqualTo(approvalSettingDTO2);
    }
}
