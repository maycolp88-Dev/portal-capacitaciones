package com.portal.DTO;

import com.portal.model.Badge;
import com.portal.model.Progress;

import java.util.List;

public class ProfileDTO {
    private UserDTO user;
    private List<Progress> progress;
    private List<Badge> badges;

    public ProfileDTO(UserDTO user, List<Progress> progress, List<Badge> badges) {
        this.user = user;
        this.progress = progress;
        this.badges = badges;
    }

    public UserDTO getUser() { return user; }
    public List<Progress> getProgress() { return progress; }
    public List<Badge> getBadges() { return badges; }
}
