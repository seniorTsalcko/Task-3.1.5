package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.core.GrantedAuthority;
import ru.kata.spring.boot_security.demo.entities.Role;

public class AppGrantedAuthority implements GrantedAuthority {
    private Role role;

    @Override
    public String getAuthority() {
        return role.getRole();
    }
}
