package me.kalko.phonebook;

import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;


/**
 * Unit test for simple PhonebookApp.
 */
public class PhonebookAppTest
{
    @ClassRule
    public static final DropwizardAppRule<PhonebookConfiguration> RULE =
            new DropwizardAppRule<PhonebookConfiguration>(PhonebookApp.class, "config.yaml");

    @Test
    public final void testTestResource()
    {
//        Client client = new Client();
//
//        ClientResponse response = client.resource(
//                String.format("http://localhost:%d/rest/v1/test", RULE.getLocalPort()))
//                .get(ClientResponse.class);
//
//        assertThat(response.getStatus(), is(200));
    }

}
