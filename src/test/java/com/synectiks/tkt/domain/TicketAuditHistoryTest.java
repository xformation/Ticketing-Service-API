package com.synectiks.tkt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synectiks.tkt.web.rest.TestUtil;

public class TicketAuditHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketAuditHistory.class);
        TicketAuditHistory ticketAuditHistory1 = new TicketAuditHistory();
        ticketAuditHistory1.setId(1L);
        TicketAuditHistory ticketAuditHistory2 = new TicketAuditHistory();
        ticketAuditHistory2.setId(ticketAuditHistory1.getId());
        assertThat(ticketAuditHistory1).isEqualTo(ticketAuditHistory2);
        ticketAuditHistory2.setId(2L);
        assertThat(ticketAuditHistory1).isNotEqualTo(ticketAuditHistory2);
        ticketAuditHistory1.setId(null);
        assertThat(ticketAuditHistory1).isNotEqualTo(ticketAuditHistory2);
    }
}
