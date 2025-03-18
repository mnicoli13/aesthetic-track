package IngDelSw.nicoli.repositories;

import IngDelSw.nicoli.model.User;
import IngDelSw.nicoli.model.enums.Gender;
import IngDelSw.nicoli.model.enums.Role;
import IngDelSw.nicoli.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryUnitTests {

    @Autowired
    private UserRepository userRepository;
    private static Integer userId;


    @Test
    @DisplayName("Test 1:Save Medic Test")
    @Order(1)
    @Rollback(value = false)
    public void saveUserTest(){


        User user = new User("name", "surname", "test@email.com", "password",  Gender.m, Role.Medic, LocalDateTime.now());


        User createdUser = userRepository.save(user);

        userId = createdUser.getId();

        System.out.println(user);
        Assertions.assertThat(user.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getUserTest(){

        User user = userRepository.findById(userId).get();
        System.out.println(user);
        Assertions.assertThat(user.getId()).isEqualTo(userId);
    }

    @Test
    @Order(3)
    public void getListOfUsersTest(){
        List<User> users = userRepository.findAll();
        System.out.println(users);
        Assertions.assertThat(users.size()).isGreaterThan(0);

    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateUserTest(){

        User user = userRepository.findById(userId).get();
        user.setEmail("samcurran@gmail.com");
        User userUpdated =  userRepository.save(user);

        System.out.println(userUpdated);
        Assertions.assertThat(userUpdated.getEmail()).isEqualTo("samcurran@gmail.com");

    }



}