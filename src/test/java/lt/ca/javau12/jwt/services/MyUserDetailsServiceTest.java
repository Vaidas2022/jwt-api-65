package lt.ca.javau12.jwt.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lt.ca.javau12.jwt.models.Role;
import lt.ca.javau12.jwt.models.User;
import lt.ca.javau12.jwt.repository.UserRepository;

class MyUserDetailsServiceTest {

    @Test
    void loadsExistingUser() {
        UserRepository repo = mock(UserRepository.class);
        User user = new User(1L, "bob", "secret", Role.USER, true);
        when(repo.findByUsername("bob")).thenReturn(Optional.of(user));
        MyUserDetailsService service = new MyUserDetailsService(repo);

        UserDetails details = service.loadUserByUsername("bob");
        assertEquals("bob", details.getUsername());
        assertEquals("secret", details.getPassword());
        assertTrue(details.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void throwsWhenUserNotFound() {
        UserRepository repo = mock(UserRepository.class);
        when(repo.findByUsername("bob")).thenReturn(Optional.empty());
        MyUserDetailsService service = new MyUserDetailsService(repo);

        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("bob"));
    }
}
