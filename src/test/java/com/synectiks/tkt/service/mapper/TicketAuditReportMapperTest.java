package com.synectiks.tkt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TicketAuditReportMapperTest {

    private TicketAuditReportMapper ticketAuditReportMapper;

    @BeforeEach
    public void setUp() {
//        ticketAuditReportMapper = new TicketAuditReportMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ticketAuditReportMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ticketAuditReportMapper.fromId(null)).isNull();
    }
}
