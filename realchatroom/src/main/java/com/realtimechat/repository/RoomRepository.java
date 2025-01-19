package com.realtimechat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.realtimechat.model.Room;
import com.realtimechat.model.User;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByRoomId(String roomId); 
    List<Room> findByCreator(User creator);
    List<Room> findByUsersContaining(User user);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_rooms WHERE room_id = :roomUniqueID AND user_id = :userID ", nativeQuery = true)
    void deleteRoomByIdNative(@Param("roomUniqueID") Long roomUniqueID, @Param("userID") Long userID);
}
