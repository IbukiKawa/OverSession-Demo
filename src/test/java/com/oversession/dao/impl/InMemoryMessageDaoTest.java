package com.oversession.dao.impl;

import com.oversession.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryMessageDaoTest {

    private InMemoryMessageDao dao;

    @BeforeEach
    void setUp() {
        dao = new InMemoryMessageDao();
    }

    @Test
    void saveAndFindById() {
        Message m = new Message("m1", "room1", "user1", "Hello!", LocalDateTime.now());
        dao.save(m);

        assertTrue(dao.findById("m1").isPresent());
        assertEquals("Hello!", dao.findById("m1").get().getMessage());
    }

    @Test
    void findByRoomId_returnsSortedByTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        dao.save(new Message("m2", "room1", "user1", "Second", now.plusSeconds(2)));
        dao.save(new Message("m1", "room1", "user2", "First", now));
        dao.save(new Message("m3", "room2", "user1", "Other room", now));

        List<Message> result = dao.findByRoomId("room1");
        assertEquals(2, result.size());
        assertEquals("m1", result.get(0).getMessageId());
        assertEquals("m2", result.get(1).getMessageId());
    }

    @Test
    void deleteById() {
        dao.save(new Message("m1", "room1", "user1", "hi", LocalDateTime.now()));
        dao.deleteById("m1");

        assertTrue(dao.findById("m1").isEmpty());
    }
}
