package com.syn.tkt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.syn.tkt.web.rest.TestUtil;

public class EmailTicketAssociationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailTicketAssociation.class);
        EmailTicketAssociation emailTicketAssociation1 = new EmailTicketAssociation();
        emailTicketAssociation1.setId(1L);
        EmailTicketAssociation emailTicketAssociation2 = new EmailTicketAssociation();
        emailTicketAssociation2.setId(emailTicketAssociation1.getId());
        assertThat(emailTicketAssociation1).isEqualTo(emailTicketAssociation2);
        emailTicketAssociation2.setId(2L);
        assertThat(emailTicketAssociation1).isNotEqualTo(emailTicketAssociation2);
        emailTicketAssociation1.setId(null);
        assertThat(emailTicketAssociation1).isNotEqualTo(emailTicketAssociation2);
    }
}
