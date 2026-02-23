package com.oversession.dao.impl;

import com.oversession.dao.RoomDao;
import com.oversession.model.Room;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * RoomDao のインメモリ実装（テスト・開発用）。
 * <p>
 * ルームとユーザーの紐付けは {@code roomUsers} マップで管理する。
 */
public class InMemoryRoomDao implements RoomDao {

    private final Map<String, Room> rooms = new ConcurrentHashMap<>();

    /** roomId → Set&lt;userId&gt; */
    private final Map<String, Set<String>> roomUsers = new ConcurrentHashMap<>();

    @Override
    public List<Room> findByUserId(String userId) {
        return roomUsers.entrySet().stream()
                .filter(e -> e.getValue().contains(userId))
                .map(e -> rooms.get(e.getKey()))
                .filter(r -> r != null)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Room> findById(String roomId) {
        return Optional.ofNullable(rooms.get(roomId));
    }

    @Override
    public void save(Room room) {
        rooms.put(room.getRoomId(), room);
        roomUsers.putIfAbsent(room.getRoomId(), ConcurrentHashMap.newKeySet());
    }

    @Override
    public void addUserToRoom(String roomId, String userId) {
        roomUsers.computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet())
                .add(userId);
    }

    @Override
    public void deleteById(String roomId) {
        rooms.remove(roomId);
        roomUsers.remove(roomId);
    }
}
