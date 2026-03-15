package com.oversession.dao.impl;

import com.oversession.model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRoomDaoTest {

    private InMemoryRoomDao dao;

    @BeforeEach
    void setUp() {
        dao = new InMemoryRoomDao();
    }

    @Test
    void saveAndFindById() {
        dao.save(new Room("r1", "General"));
        assertTrue(dao.findById("r1").isPresent());
        assertEquals("General", dao.findById("r1").get().getRoomName());
    }

    @Test
    void addUserToRoomAndFindByUserId() {
        dao.save(new Room("r1", "General"));
        dao.save(new Room("r2", "Random"));
        dao.addUserToRoom("r1", "user1");
        dao.addUserToRoom("r2", "user1");
        dao.addUserToRoom("r2", "user2");

        List<Room> user1Rooms = dao.findByUserId("user1");
        assertEquals(2, user1Rooms.size());

        List<Room> user2Rooms = dao.findByUserId("user2");
        assertEquals(1, user2Rooms.size());
        assertEquals("r2", user2Rooms.get(0).getRoomId());
    }

    @Test
    void deleteById() {
        dao.save(new Room("r1", "General"));
        dao.addUserToRoom("r1", "user1");
        dao.deleteById("r1");

        assertTrue(dao.findById("r1").isEmpty());
        assertTrue(dao.findByUserId("user1").isEmpty());
    }
}
