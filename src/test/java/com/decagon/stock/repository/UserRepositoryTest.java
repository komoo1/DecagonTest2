package com.decagon.stock.repository;

import com.decagon.stock.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Victor.Komolafe
 */
@RunWith(SpringRunner.class)
@DataJpaTest()
@TestPropertySource("classpath:application.properties")
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private UUID testUUid = UUID.randomUUID();
    private User user1, user2;

    @Before
    public void init(){
        assertThat(entityManager).isNotNull();
        assertThat(userRepository).isNotNull();

        user1 = new User();
        user1.setUsername("test");
        user1.setPasswordHash(BCrypt.hashpw("pass", BCrypt.gensalt()));

        user2 = new User();
        user1.setUsername("test2");
        user1.setPasswordHash(BCrypt.hashpw("pass", BCrypt.gensalt()));
        user2.setUuid(testUUid);

        entityManager.persist(user1);
        entityManager.persist(user2);
    }

    @Test
    public void testFindFirstByUsernameIgnoreCase(){
        Optional<User> testUser = userRepository.findFirstByUsernameIgnoreCase("test");
        assertTrue(testUser.isPresent());
        assertThat(testUser.get()).extracting(User::getUsername).isEqualTo("test");
    }

    @Test
    public void testFindFirstByUuidNotFound(){
        Optional<User> testUser = userRepository.findFirstByUuid(UUID.randomUUID());
        assertFalse(testUser.isPresent());
    }

    @Test
    public void testFindFirstByUuidFound(){
        Optional<User> testUser = userRepository.findFirstByUuid(testUUid);
        assertFalse(testUser.isPresent());
        assertThat(testUser.get()).extracting(User::getUsername).isEqualTo("test2");
    }
}
