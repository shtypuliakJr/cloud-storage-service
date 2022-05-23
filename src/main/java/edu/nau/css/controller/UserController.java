package edu.nau.css.controller;

import edu.nau.css.dto.user.UserDTO;
import edu.nau.css.dto.user.UserRegistrationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static edu.nau.css.constants.Endpoint.*;

@RequestMapping(API_PART)
public interface UserController {

    @PostMapping(REGISTRATION_ENDPOINT)
    ResponseEntity<UserDTO> registration(UserDTO userRegistrationDTO);

    @PostMapping(LOGIN_ENDPOINT)
    Object login(UserRegistrationDTO userRegistrationDTO);

}
