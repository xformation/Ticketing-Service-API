package com.synectiks.tkt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synectiks.tkt.web.rest.TestUtil;

public class TicketAuditReportTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketAuditReport.class);
        TicketAuditReport ticketAuditReport1 = new TicketAuditReport();
        ticketAuditReport1.setId(1L);
        TicketAuditReport ticketAuditReport2 = new TicketAuditReport();
        ticketAuditReport2.setId(ticketAuditReport1.getId());
        assertThat(ticketAuditReport1).isEqualTo(ticketAuditReport2);
        ticketAuditReport2.setId(2L);
        assertThat(ticketAuditReport1).isNotEqualTo(ticketAuditReport2);
        ticketAuditReport1.setId(null);
        assertThat(ticketAuditReport1).isNotEqualTo(ticketAuditReport2);
    }
}
