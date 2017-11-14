package me.kalko.phonebook.resources;

import com.google.common.collect.Lists;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.testing.junit.ResourceTestRule;
import me.kalko.phonebook.JwtAuthFilter;
import me.kalko.phonebook.Utils;
import me.kalko.phonebook.domain.Contact;
import me.kalko.phonebook.domain.User;
import me.kalko.phonebook.domain.dao.ContactDao;
import me.kalko.phonebook.domain.dao.ContactService;
import me.kalko.phonebook.domain.dao.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ContactResourceTest {
    private static final ContactDao CONTACT_DAO = mock(ContactDao.class);
    private static final UserService USER_SERVICE = mock(UserService.class);

    @ClassRule
    public static final ResourceTestRule RESOURCES = ResourceTestRule.builder()
            .addResource(new ContactResource(new ContactService() {
                @Override
                protected ContactDao contactDao() {
                    return CONTACT_DAO;
                }
            }))
            .addProvider(new AuthValueFactoryProvider.Binder(User.class))
            .addProvider(new AuthDynamicFeature(new JwtAuthFilter(USER_SERVICE)))
            .build();
    private User user;

    @Before
    public void setUp() {
        user = new User("test", "pass");
    }

    @After
    public void tearDown() {
        reset(CONTACT_DAO);
    }

    @Test
    public void getContact() throws Exception {
        List<Contact> contactList = Lists.newArrayList(new Contact(0, 0, "name", "lastname"),
                new Contact(1, 0, "name2", "lastname2"));
        when(CONTACT_DAO.getContacts(0L)).thenReturn(contactList);

        when(USER_SERVICE.getUser(any(String.class))).thenReturn(user);

        UriInfo mockUriInfo = mock(UriInfo.class);
        when(mockUriInfo.getAbsolutePath()).thenReturn(new URI("http://localhost"));

        String issuedToken = Utils.issueToken(user.getName(), mockUriInfo);

        final Response response = RESOURCES.target("/contacts")
                .request(MediaType.APPLICATION_JSON)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Token", issuedToken)
                .get();

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        List<Contact> responseContacts = response.readEntity(new GenericType<List<Contact>>() {});
        assertThat(responseContacts).isEqualTo(contactList);
    }
}