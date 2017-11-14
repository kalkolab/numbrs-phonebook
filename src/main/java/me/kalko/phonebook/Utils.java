package me.kalko.phonebook;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.UriInfo;
import java.security.Key;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    private static final String KEY_STRING = "jwtAuthKey";
    public static final Key KEY = new SecretKeySpec(KEY_STRING.getBytes(), 0, KEY_STRING.getBytes().length, "DES");
    public static final String DELIMITER = ",";

    public static String issueToken(String login, UriInfo uriInfo) {
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();
        logger.debug("Generated token for a key : " + jwtToken + " - " + KEY);
        return jwtToken;
    }

    public static Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(Utils.KEY).parseClaimsJws(token).getBody();
    }

    public static String digestPassword(String plainTextPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(plainTextPassword.getBytes("UTF-8"));
            byte[] passwordDigest = md.digest();
            return new String(Base64.getEncoder().encode(passwordDigest));
        } catch (Exception e) {
            throw new RuntimeException("Exception encoding password", e);
        }
    }

    private static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String serializeList(List<String> list) {
        return Optional.ofNullable(list).map(List::stream).orElseGet(Stream::empty).collect(Collectors.joining(DELIMITER));
    }

    public static List<String> deserializeList(String string) {
        return StringUtils.isNoneEmpty(string) ? new ArrayList(Arrays.asList(string.split(DELIMITER))) : new ArrayList();
    }
}
