package com.realtimechat.model;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "display_name", nullable = false)
    private String displayName;
    
    @Column(name = "SQ1", nullable = false)
    private String securityQuestion1;
    
    @Column(name = "SQ2", nullable = false)
    private String securityQuestion2;

    // Relationships
    @OneToMany(mappedBy = "creator")
    @JsonIgnore
    private List<Room> createdRooms;

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private List<Room> joinedRooms;

    public User() {}

    public User(String email, String password, String displayName) {
        this.email = email;
        this.password = password;
        this.displayName = displayName;
    }
    
    public User(String email, String password, String displayName, String securityQuestion1,
                String securityQuestion2) {
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.securityQuestion1 = securityQuestion1;
        this.securityQuestion2 = securityQuestion2;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getSecurityQuestion1() {
        return securityQuestion1;
    }

    public void setSecurityQuestion1(String securityQuestion1) {
        this.securityQuestion1 = securityQuestion1;
    }
    
    public String getSecurityQuestion2() {
        return securityQuestion2;
    }

    public void setSecurityQuestion2(String securityQuestion2) {
        this.securityQuestion2 = securityQuestion2;
    }

    public List<Room> getCreatedRooms() {
        return createdRooms;
    }

    public void setCreatedRooms(List<Room> createdRooms) {
        this.createdRooms = createdRooms;
    }

    public List<Room> getJoinedRooms() {
        return joinedRooms;
    }

    public void setJoinedRooms(List<Room> joinedRooms) {
        this.joinedRooms = joinedRooms;
    }
}
