package com.synectiks.tkt.service.dto;

import com.synectiks.tkt.web.rest.TestUtil;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EmailTicketAssociationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailTicketAssociationDTO.class);
        EmailTicketAssociationDTO emailTicketAssociationDTO1 = new EmailTicketAssociationDTO();
        emailTicketAssociationDTO1.setId(1L);
        EmailTicketAssociationDTO emailTicketAssociationDTO2 = new EmailTicketAssociationDTO();
        assertThat(emailTicketAssociationDTO1).isNotEqualTo(emailTicketAssociationDTO2);
        emailTicketAssociationDTO2.setId(emailTicketAssociationDTO1.getId());
        assertThat(emailTicketAssociationDTO1).isEqualTo(emailTicketAssociationDTO2);
        emailTicketAssociationDTO2.setId(2L);
        assertThat(emailTicketAssociationDTO1).isNotEqualTo(emailTicketAssociationDTO2);
        emailTicketAssociationDTO1.setId(null);
        assertThat(emailTicketAssociationDTO1).isNotEqualTo(emailTicketAssociationDTO2);
    }
}
