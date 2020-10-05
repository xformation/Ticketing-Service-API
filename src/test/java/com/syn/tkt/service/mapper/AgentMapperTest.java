package com.syn.tkt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AgentMapperTest {

    private AgentMapper agentMapper;

    @BeforeEach
    public void setUp() {
//        agentMapper = new AgentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(agentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(agentMapper.fromId(null)).isNull();
    }
}
