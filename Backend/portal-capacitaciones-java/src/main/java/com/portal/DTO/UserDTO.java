package com.portal.DTO;

public class UserDTO {
    private Long id;
    private String username;
    private boolean admin;

    public UserDTO(Long id, String username, boolean admin) {
        this.id = id;
        this.username = username;
        this.admin = admin;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public boolean isAdmin() { return admin; }
}
