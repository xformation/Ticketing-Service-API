package com.syn.tkt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EmailTicketAssociationMapperTest {

    private EmailTicketAssociationMapper emailTicketAssociationMapper;

    @BeforeEach
    public void setUp() {
//        emailTicketAssociationMapper = new EmailTicketAssociationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(emailTicketAssociationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(emailTicketAssociationMapper.fromId(null)).isNull();
    }
}
