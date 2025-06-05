package lt.ca.javau12.jwt.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JwtUtilsTest {

    private static final String SECRET = "manolabaiilgakazkokiaraktueilute";
    private static final long EXPIRATION_MS = 3600000; // 1h

    @Test
    void generatedTokenContainsSameUsername() {
        JwtUtils utils = new JwtUtils(SECRET, EXPIRATION_MS);
        String token = utils.generateToken("alice");
        assertEquals("alice", utils.extractUsername(token));
    }

    @Test
    void validateTokenWithCorrectUsername() {
        JwtUtils utils = new JwtUtils(SECRET, EXPIRATION_MS);
        String token = utils.generateToken("alice");
        assertTrue(utils.validateToken(token, "alice"));
    }

    @Test
    void validateTokenWithIncorrectUsername() {
        JwtUtils utils = new JwtUtils(SECRET, EXPIRATION_MS);
        String token = utils.generateToken("alice");
        assertFalse(utils.validateToken(token, "bob"));
    }

    @Test
    void validateTokenReturnsFalseWhenExpired() throws InterruptedException {
        JwtUtils utils = new JwtUtils(SECRET, 1); // token expires almost immediately
        String token = utils.generateToken("alice");
        Thread.sleep(5);
        assertFalse(utils.validateToken(token, "alice"));
    }
}
