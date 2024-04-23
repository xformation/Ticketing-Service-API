package com.synectiks.tkt.service.dto;

import com.synectiks.tkt.web.rest.TestUtil;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TicketAuditHistoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketAuditHistoryDTO.class);
        TicketAuditHistoryDTO ticketAuditHistoryDTO1 = new TicketAuditHistoryDTO();
        ticketAuditHistoryDTO1.setId(1L);
        TicketAuditHistoryDTO ticketAuditHistoryDTO2 = new TicketAuditHistoryDTO();
        assertThat(ticketAuditHistoryDTO1).isNotEqualTo(ticketAuditHistoryDTO2);
        ticketAuditHistoryDTO2.setId(ticketAuditHistoryDTO1.getId());
        assertThat(ticketAuditHistoryDTO1).isEqualTo(ticketAuditHistoryDTO2);
        ticketAuditHistoryDTO2.setId(2L);
        assertThat(ticketAuditHistoryDTO1).isNotEqualTo(ticketAuditHistoryDTO2);
        ticketAuditHistoryDTO1.setId(null);
        assertThat(ticketAuditHistoryDTO1).isNotEqualTo(ticketAuditHistoryDTO2);
    }
}
