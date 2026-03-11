package com.anshulp.springsecurity.entity;

import lombok.Getter;

import java.util.Set;

@Getter
public enum Role {

    USER(Set.of(Permission.MISSION_READ)),
    ADMIN(Set.of(Permission.MISSION_CREATE, Permission.MISSION_DELETE, Permission.MISSION_UPDATE, Permission.MISSION_READ, Permission.ADMIN_CREATE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

}
