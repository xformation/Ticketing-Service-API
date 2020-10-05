package com.syn.tkt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TicketHistoryMapperTest {

    private TicketHistoryMapper ticketHistoryMapper;

    @BeforeEach
    public void setUp() {
//        ticketHistoryMapper = new TicketHistoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ticketHistoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ticketHistoryMapper.fromId(null)).isNull();
    }
}
