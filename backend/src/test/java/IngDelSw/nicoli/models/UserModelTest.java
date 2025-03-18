package IngDelSw.nicoli.models;

import IngDelSw.nicoli.model.User;
import IngDelSw.nicoli.model.enums.Gender;
import IngDelSw.nicoli.model.enums.Role;

import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class UserModelTest {

    private User user = new User("firstName", "lastName", "example@email.com", "password123", Gender.m, Role.User, LocalDateTime.now());

    @Test
    public void testUserCreation() {
        assertNotNull(user);
        assertEquals("firstName", user.getFirstName());
        assertEquals("lastName", user.getLastName());
        assertEquals("example@email.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals(Gender.m, user.getGender());
        assertEquals(Role.User, user.getRole());
        assertNotNull(user.getActivatedAt());
    }

    @Test
    public void testSettersAndGetters() {
        user.setFirstName("Jane");
        assertEquals("Jane", user.getFirstName());

        user.setLastName("Smith");
        assertEquals("Smith", user.getLastName());

        user.setEmail("janesmith@email.com");
        assertEquals("janesmith@email.com", user.getEmail());

        user.setPassword("newpassword");
        assertEquals("newpassword", user.getPassword());

        user.setGender(Gender.f);
        assertEquals(Gender.f, user.getGender());

        user.setRole(Role.Admin);
        assertEquals(Role.Admin, user.getRole());

        LocalDateTime now = LocalDateTime.now();
        user.setActivatedAt(now);
        assertEquals(now, user.getActivatedAt());
    }

    @Test
    public void testRoleMethods() {
        user.setRole(Role.Admin);
        assertEquals(Role.Admin, user.getRole());

        user.setRole(Role.Medic);
        assertEquals(Role.Medic, user.getRole());
    }

    @Test
    public void testIdSetterAndGetter() {
        user.setId(1);
        assertEquals(Integer.valueOf(1), user.getId());
    }

    @Test
    public void testDefaultConstructor() {
        User emptyUser = new User();
        assertNotNull(emptyUser);
        assertNull(emptyUser.getFirstName());
        assertNull(emptyUser.getLastName());
        assertNull(emptyUser.getEmail());
        assertNull(emptyUser.getPassword());
        assertNull(emptyUser.getGender());
        assertNull(emptyUser.getRole());
        assertNull(emptyUser.getActivatedAt());
    }
}

