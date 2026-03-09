package com.oversession.dao.impl;

import com.oversession.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserDaoTest {

    private InMemoryUserDao userDao;

    @BeforeEach
    void setUp() {
        userDao = new InMemoryUserDao();
    }

    @Test
    void saveAndFindByUserId() {
        User user = new User();
        user.setUserName("Alice");
        user.setDepartmentName("Engineering");
        user.setWorkingStatus("出社");
        
        String userId = userDao.save(user);
        assertNotNull(userId);
        assertTrue(userId.startsWith("user"));

        Optional<User> found = userDao.findByUserId(userId);
        assertTrue(found.isPresent());
        assertEquals("Alice", found.get().getUserName());
        assertEquals("出社", found.get().getWorkingStatus());
    }

    @Test
    void findAll_excludesDeletedUsers() {
        User user1 = new User();
        user1.setUserName("Alice");
        String userId1 = userDao.save(user1);

        User user2 = new User();
        user2.setUserName("Bob");
        String userId2 = userDao.save(user2);

        List<User> allUsers = userDao.findAll();
        assertEquals(2, allUsers.size());

        // user1を削除
        userDao.deleteByUserId(userId1);

        List<User> activeUsers = userDao.findAll();
        assertEquals(1, activeUsers.size());
        assertEquals("Bob", activeUsers.get(0).getUserName());
    }

    @Test
    void searchUsers_byName() {
        User alice = new User();
        alice.setUserName("Alice Smith");
        alice.setDepartmentName("Engineering");
        userDao.save(alice);

        User bob = new User();
        bob.setUserName("Bob Johnson");
        bob.setDepartmentName("Marketing");
        userDao.save(bob);

        List<User> results = userDao.findBySearchCondition("alice");
        assertEquals(1, results.size());
        assertEquals("Alice Smith", results.get(0).getUserName());

        List<User> engineeringResults = userDao.findBySearchCondition("engineering");
        assertEquals(1, engineeringResults.size());
        assertEquals("Alice Smith", engineeringResults.get(0).getUserName());
    }

    @Test
    void updateUser() {
        User user = new User();
        user.setUserName("Alice");
        user.setWorkingStatus("不在");
        String userId = userDao.save(user);

        user.setWorkingStatus("出社");
        user.setPrimaryHeadOfficeName("Tokyo Office");
        userDao.update(user);

        Optional<User> updated = userDao.findByUserId(userId);
        assertTrue(updated.isPresent());
        assertEquals("出社", updated.get().getWorkingStatus());
        assertEquals("Tokyo Office", updated.get().getPrimaryHeadOfficeName());
    }
}