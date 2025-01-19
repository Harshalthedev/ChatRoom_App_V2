package com.realtimechat.controller;

import com.realtimechat.model.Room;
import com.realtimechat.service.RoomService;
import com.realtimechat.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@SessionAttributes("room")
public class RoomController {

    @Autowired
    private RoomService roomService;
    
    @Autowired
    private UserService userService;
    
    @ModelAttribute("room")
    public Room getRoom() {
        return new Room();
    }

    // Get a room by its roomId
    @GetMapping("/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable("roomId") String roomId) {
        Room room = roomService.getRoomById(roomId);
        if (room == null) {
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(room); 
    }

    // Get all rooms created or joined by the current user
    @GetMapping("/manage")
    public ResponseEntity<List<Room>> getRoomsForCurrentUser() {
        try {
            List<Room> rooms = roomService.getRoomsForCurrentUser();
            return ResponseEntity.ok(rooms); 
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    // GET request to handle view chat room page
    @GetMapping("/chat-room")
    public ModelAndView showChatRoomPage(@ModelAttribute("room") Room room) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLoggedUser = userService.findByEmail(authentication.getName()).getDisplayName();
        
        ModelAndView modelAndView = new ModelAndView("chatroom");
        modelAndView.addObject("room", room); 
        modelAndView.addObject("currentLoggedUser", currentLoggedUser);
        
        return modelAndView;
    }
    
    // POST request to handle create new room
    @PostMapping("/create")
    public RedirectView createRoom(@RequestParam("roomName") String roomName, RedirectAttributes redirectAttributes) {
        try {
            Room createdRoom = roomService.createRoom(roomName);
            redirectAttributes.addFlashAttribute("room", createdRoom);
            return new RedirectView("/rooms/chat-room");
        } catch (Exception e) {
            return new RedirectView("/error");
        }
    }
    
    // POST request to handle joining a chat room
    @PostMapping("/chat-page/{roomId}")
    public RedirectView showRoomPage(@PathVariable("roomId") String roomId, RedirectAttributes redirectAttributes) {
        
        Room roomDetail = roomService.getRoomById(roomId);

        if (roomDetail != null) {
            redirectAttributes.addFlashAttribute("room", roomDetail);
            return new RedirectView("/rooms/chat-room");
        }

        return new RedirectView("/error");
    }

    //Post request to handle join new room
    @PostMapping("/join")
    public RedirectView joinRoom(@RequestParam("roomCode") String roomId, RedirectAttributes redirectAttributes) {
        try {
        	System.out.println("This api is hitted");
            roomService.joinRoom(roomId);
            Room roomDetail = roomService.getRoomById(roomId);
            redirectAttributes.addFlashAttribute("room", roomDetail);
            return new RedirectView("/rooms/chat-room");
        } catch (Exception e) {
            return new RedirectView("/error");
        }
    }
    
    //Post request to handle updating room
    @PostMapping("/update/{roomId}")
    public RedirectView updateRoomName(@PathVariable("roomId") String roomId, @RequestParam("newName") String newRoomName, Model model) {
        try {
            Room updatedRoom = roomService.updateRoomName(roomId, newRoomName);
            model.addAttribute("room", updatedRoom);
            return new RedirectView("/dashboard");
        } catch (Exception e) {
            model.addAttribute("error", "Error updating room name: " + e.getMessage());
            return new RedirectView("error");
            
        }
    }
    
    //Post request to handle deleting room details
    @PostMapping("/delete/{roomId}")
    public RedirectView deleteRoom(@PathVariable("roomId") String roomId,@RequestParam("userChoice") String userChoice, Model model) {
        try {
        	if(userChoice.equalsIgnoreCase("yes")) {
	        	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	            Long displayUserID = userService.findByEmail(authentication.getName()).getId();
	            roomService.removeUserFromRoom(roomId, displayUserID);
        	}
            return new RedirectView("/dashboard"); 
        	
        } catch (Exception e) {
            model.addAttribute("error", "Error deleting the room: " + e.getMessage());
            return new RedirectView("error");
        }
    }


}
