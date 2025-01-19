package com.realtimechat.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.realtimechat.model.Room;
import com.realtimechat.model.User;
import com.realtimechat.repository.RoomRepository;
import com.realtimechat.repository.UserRepository;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a room with a user as the creator
    public Room createRoom(String roomName) {
        String roomId = generateRandomRoomId();
        String userEmail = getCurrentUserEmail();
        
        User user = userRepository.findByEmail(userEmail); 
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        
        // Ensure the room name is unique
        List<Room> existingRooms = roomRepository.findAll();
        for (Room room : existingRooms) {
            if (room.getRoomName().equalsIgnoreCase(roomName)) {
                throw new RuntimeException("A room with this name already exists");
            }
        }

        Room room = new Room(roomId, roomName, user); 
        return roomRepository.save(room);
    }

    // Get a room by its unique roomId
    public Room getRoomById(String roomId) {
        Room room = roomRepository.findByRoomId(roomId);
        if (room == null) {
            throw new RuntimeException("Room not found");
        }
        return room;
    }

    // Generate a random roomId
    private String generateRandomRoomId() {
        return UUID.randomUUID().toString();
    }

    // Get the current logged-in user's email
    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); 
    }

    // Fetch all rooms created by or joined by the current user
    public List<Room> getRoomsForCurrentUser() {
        String userEmail = getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        
        // Fetch rooms either created by or joined by the user
        List<Room> createdRooms = roomRepository.findByCreator(user);
        List<Room> joinedRooms = roomRepository.findByUsersContaining(user);

        // Combine both lists, avoiding duplicates
        Set<Room> combinedRooms = new HashSet<>(createdRooms);
        combinedRooms.addAll(joinedRooms);
        return List.copyOf(combinedRooms);
    }

    // Join a room by adding the current user to it
    public void joinRoom(String roomId) {
        String userEmail = getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Room room = roomRepository.findByRoomId(roomId);
        if (room == null) {
            throw new RuntimeException("Room not found");
        }
        
        if (!room.getUsers().contains(user)) {
            room.getUsers().add(user);
            roomRepository.save(room); 
        } else {
            throw new RuntimeException("User already joined the room");
        }
    }
    
    public Room updateRoomName(String roomId, String newRoomName) {
        Room room = roomRepository.findByRoomId(roomId);
        System.out.print(roomId);
        if (room == null) {
            throw new RuntimeException("Room not found");
        }
        room.setRoomName(newRoomName);
        return roomRepository.save(room);
    }
    
    @Transactional
    public void removeUserFromRoom(String roomId, Long userId) {
        Room room = roomRepository.findByRoomId(roomId);

        if (room != null && room.getCreator().getId() == userId) {
            // getting actual id from DB
        	Long roomUniqueID = room.getId();
            roomRepository.deleteById(roomUniqueID); 
        } else if(room != null){
        	Long roomUniqueID = room.getId();
        	roomRepository.deleteRoomByIdNative(roomUniqueID, userId);
        } else{
            throw new RuntimeException("User is not part of the room");
        }
    }

}