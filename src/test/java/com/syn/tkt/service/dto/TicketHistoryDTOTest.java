package com.syn.tkt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.syn.tkt.web.rest.TestUtil;

public class TicketHistoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketHistoryDTO.class);
        TicketHistoryDTO ticketHistoryDTO1 = new TicketHistoryDTO();
        ticketHistoryDTO1.setId(1L);
        TicketHistoryDTO ticketHistoryDTO2 = new TicketHistoryDTO();
        assertThat(ticketHistoryDTO1).isNotEqualTo(ticketHistoryDTO2);
        ticketHistoryDTO2.setId(ticketHistoryDTO1.getId());
        assertThat(ticketHistoryDTO1).isEqualTo(ticketHistoryDTO2);
        ticketHistoryDTO2.setId(2L);
        assertThat(ticketHistoryDTO1).isNotEqualTo(ticketHistoryDTO2);
        ticketHistoryDTO1.setId(null);
        assertThat(ticketHistoryDTO1).isNotEqualTo(ticketHistoryDTO2);
    }
}
