package com.synectiks.tkt.service.dto;

import com.synectiks.tkt.web.rest.TestUtil;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TicketDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketDTO.class);
        TicketDTO ticketDTO1 = new TicketDTO();
        ticketDTO1.setId(1L);
        TicketDTO ticketDTO2 = new TicketDTO();
        assertThat(ticketDTO1).isNotEqualTo(ticketDTO2);
        ticketDTO2.setId(ticketDTO1.getId());
        assertThat(ticketDTO1).isEqualTo(ticketDTO2);
        ticketDTO2.setId(2L);
        assertThat(ticketDTO1).isNotEqualTo(ticketDTO2);
        ticketDTO1.setId(null);
        assertThat(ticketDTO1).isNotEqualTo(ticketDTO2);
    }
}
