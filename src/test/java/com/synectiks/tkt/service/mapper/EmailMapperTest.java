package com.synectiks.tkt.service.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EmailMapperTest {

    private EmailMapper emailMapper;

    @BeforeEach
    public void setUp() {
//        emailMapper = new EmailMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        Assertions.assertThat(emailMapper.fromId(id).getId()).isEqualTo(id);
        Assertions.assertThat(emailMapper.fromId(null)).isNull();
    }
}
