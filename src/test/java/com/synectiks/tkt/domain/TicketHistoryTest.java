package com.synectiks.tkt.domain;

import com.synectiks.tkt.web.rest.TestUtil;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TicketHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketHistory.class);
        TicketHistory ticketHistory1 = new TicketHistory();
        ticketHistory1.setId(1L);
        TicketHistory ticketHistory2 = new TicketHistory();
        ticketHistory2.setId(ticketHistory1.getId());
        assertThat(ticketHistory1).isEqualTo(ticketHistory2);
        ticketHistory2.setId(2L);
        assertThat(ticketHistory1).isNotEqualTo(ticketHistory2);
        ticketHistory1.setId(null);
        assertThat(ticketHistory1).isNotEqualTo(ticketHistory2);
    }
}
