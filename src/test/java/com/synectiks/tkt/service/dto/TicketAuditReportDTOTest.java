package com.synectiks.tkt.service.dto;

import com.synectiks.tkt.web.rest.TestUtil;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TicketAuditReportDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketAuditReportDTO.class);
        TicketAuditReportDTO ticketAuditReportDTO1 = new TicketAuditReportDTO();
        ticketAuditReportDTO1.setId(1L);
        TicketAuditReportDTO ticketAuditReportDTO2 = new TicketAuditReportDTO();
        assertThat(ticketAuditReportDTO1).isNotEqualTo(ticketAuditReportDTO2);
        ticketAuditReportDTO2.setId(ticketAuditReportDTO1.getId());
        assertThat(ticketAuditReportDTO1).isEqualTo(ticketAuditReportDTO2);
        ticketAuditReportDTO2.setId(2L);
        assertThat(ticketAuditReportDTO1).isNotEqualTo(ticketAuditReportDTO2);
        ticketAuditReportDTO1.setId(null);
        assertThat(ticketAuditReportDTO1).isNotEqualTo(ticketAuditReportDTO2);
    }
}
