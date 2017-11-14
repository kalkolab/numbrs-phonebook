package me.kalko.phonebook;

import io.jsonwebtoken.Claims;
import org.assertj.core.util.Lists;
import org.junit.Test;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UtilsTest {
    @Test
    public void token() throws Exception {
        UriInfo mockUriInfo = mock(UriInfo.class);
        when(mockUriInfo.getAbsolutePath()).thenReturn(new URI("http://localhost"));

        String issuedToken = Utils.issueToken("test", mockUriInfo);

        Claims claims = Utils.parseToken(issuedToken);
        assertEquals("test", claims.getSubject());
    }

    @Test
    public void deserializeList() throws Exception {
        List<String> phonesList = Lists.newArrayList("+7915123134564", "+1 (343) 234-564-234");

        assertEquals(phonesList, Utils.deserializeList(Utils.serializeList(phonesList)));
    }

}