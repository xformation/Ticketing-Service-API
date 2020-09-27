package com.syn.tkt.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TicketAuditHistoryMapperTest {

    private TicketAuditHistoryMapper ticketAuditHistoryMapper;

    @BeforeEach
    public void setUp() {
//        ticketAuditHistoryMapper = new TicketAuditHistoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ticketAuditHistoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ticketAuditHistoryMapper.fromId(null)).isNull();
    }
}
