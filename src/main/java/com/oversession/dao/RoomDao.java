package com.oversession.dao;

import com.oversession.model.Room;

import java.util.List;
import java.util.Optional;

/**
 * チャットルーム DAO インターフェース。
 * <ul>
 *   <li>GET /api/rooms — ルーム一覧取得（UserIdで検索、GSI: UserRoomsIndex 想定）</li>
 * </ul>
 */
public interface RoomDao {

    /**
     * ユーザーが参加しているルーム一覧を取得する。
     */
    List<Room> findByUserId(String userId);

    /**
     * ルームIDで1件取得する。
     */
    Optional<Room> findById(String roomId);

    /**
     * ルームを保存する。
     */
    void save(Room room);

    /**
     * ルームにユーザーを追加する。
     */
    void addUserToRoom(String roomId, String userId);

    /**
     * ルームを削除する。
     */
    void deleteById(String roomId);
}
