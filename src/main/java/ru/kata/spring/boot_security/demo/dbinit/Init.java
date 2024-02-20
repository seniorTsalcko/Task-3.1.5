package ru.kata.spring.boot_security.demo.dbinit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init implements ApplicationListener<ContextRefreshedEvent> {

    private RoleServiceImpl roleService;
    private PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public Init(RoleServiceImpl roleService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public  void onApplicationEvent(ContextRefreshedEvent event) {

        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleService.save(userRole);

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleService.save(adminRole);

        Set<Role> userRoles = new HashSet<>(Arrays.asList(userRole));
        Set<Role> adminRoles = new HashSet<>(Arrays.asList(adminRole));

        User admin = new User ();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEmail("admin@mail.ru");
        admin.setRoles(adminRoles);

        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setEmail("user@mail.ru");
        user.setRoles(userRoles);

        userRepository.save(admin);
        userRepository.save(user);
    }
}