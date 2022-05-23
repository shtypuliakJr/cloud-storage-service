package edu.nau.css.controller.impl;

import edu.nau.css.controller.UserController;
import edu.nau.css.domain.User;
import edu.nau.css.dto.user.UserDTO;
import edu.nau.css.dto.user.UserRegistrationDTO;
import edu.nau.css.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerImpl implements UserController {

    @Autowired
    private RegistrationService registrationService;

    @Override
    public ResponseEntity<UserDTO> registration(@ModelAttribute UserDTO userDTO) {

        User user = registrationService.register(User.builder()
                .email(userDTO.getEmail())
                .name(userDTO.getUsername())
                .password(userDTO.getPassword())
                .build());

        return ResponseEntity.ok(UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build());
    }

    @Override
    public Object login(UserRegistrationDTO userRegistrationDTO) {


        return null;
    }

}
