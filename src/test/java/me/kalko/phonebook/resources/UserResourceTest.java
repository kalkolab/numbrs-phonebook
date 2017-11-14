package me.kalko.phonebook.resources;

import io.dropwizard.testing.junit.ResourceTestRule;
import me.kalko.phonebook.Utils;
import me.kalko.phonebook.domain.Token;
import me.kalko.phonebook.domain.User;
import me.kalko.phonebook.domain.dao.UserDao;
import me.kalko.phonebook.domain.dao.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

    private static final UserDao USER_DAO = mock(UserDao.class);

    @ClassRule
    public static final ResourceTestRule RESOURCES = ResourceTestRule.builder()
            .addResource(new UserResource(new UserService() {
                @Override
                protected UserDao userDao() {
                    return USER_DAO;
                }
            }))
            .build();
    private User user;

    @Before
    public void setUp() {
        user = new User("test", "pass");
    }

    @After
    public void tearDown() {
        reset(USER_DAO);
    }

    @Test
    public void login() throws Exception {
        when(USER_DAO.getUser(any(String.class))).thenReturn(new User(user.getName(), Utils.digestPassword(user.getPassword())));

        final Response response = RESOURCES.target("/user")
                .request(MediaType.APPLICATION_JSON)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(Entity.json("{ \"username\": \"test\", \"password\": \"pass\"}"));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.CREATED);
        Token token = response.readEntity(Token.class);
        assertThat(Utils.parseToken(token.token).getSubject()).isEqualTo(user.getName());
    }

}