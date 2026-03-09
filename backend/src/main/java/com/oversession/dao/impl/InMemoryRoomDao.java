package com.oversession.dao.impl;

import com.oversession.model.Room;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * RoomDao のインメモリ実装（テスト・開発用）
 */
public class InMemoryRoomDao {

    private final Map<String, Room> rooms = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> roomUsers = new ConcurrentHashMap<>();
    private int idCounter = 1;

    public void save(Room room) {
        if (room.getRoomId() == null) {
            room.setRoomId("room" + (idCounter++));
        }
        rooms.put(room.getRoomId(), room);
    }

    public Optional<Room> findById(String roomId) {
        return Optional.ofNullable(rooms.get(roomId));
    }

    public List<Room> findAll() {
        return rooms.values().stream().collect(Collectors.toList());
    }

    public void addUserToRoom(String roomId, String userId) {
        roomUsers.computeIfAbsent(roomId, k -> new HashSet<>()).add(userId);
    }

    public List<Room> findByUserId(String userId) {
        return roomUsers.entrySet().stream()
                .filter(entry -> entry.getValue().contains(userId))
                .map(entry -> rooms.get(entry.getKey()))
                .filter(room -> room != null)
                .collect(Collectors.toList());
    }

    public void deleteById(String roomId) {
        rooms.remove(roomId);
        roomUsers.remove(roomId);
    }

    public void clear() {
        rooms.clear();
        roomUsers.clear();
    }
}