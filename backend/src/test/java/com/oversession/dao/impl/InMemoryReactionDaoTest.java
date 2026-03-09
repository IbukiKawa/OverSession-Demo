package com.oversession.dao.impl;

import com.oversession.model.Reaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryReactionDaoTest {

    private InMemoryReactionDao dao;

    @BeforeEach
    void setUp() {
        dao = new InMemoryReactionDao();
    }

    @Test
    void saveAndFindByMessageId() {
        dao.save(new Reaction("m1", "user1", "👍"));
        dao.save(new Reaction("m1", "user2", "❤️"));
        dao.save(new Reaction("m2", "user1", "😀"));

        List<Reaction> result = dao.findByMessageId("m1");
        assertEquals(2, result.size());
    }

    @Test
    void deleteRemovesMatchingReaction() {
        dao.save(new Reaction("m1", "user1", "👍"));
        dao.save(new Reaction("m1", "user1", "❤️"));

        dao.delete("m1", "user1", "👍");

        List<Reaction> result = dao.findByMessageId("m1");
        assertEquals(1, result.size());
        assertEquals("❤️", result.get(0).getEmoji());
    }
}
