package edu.nau.css.service.impl;

import edu.nau.css.domain.User;
import edu.nau.css.exception.UserAlreadyExistsException;
import edu.nau.css.repository.UserRepository;
import edu.nau.css.service.MetaDataService;
import edu.nau.css.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MetaDataService metaDataService;

    public User register(User user) {

        if (userRepository.existsByEmail(user.getEmail()))
            throw new UserAlreadyExistsException("User already exists");

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User newUser = userRepository.save(user);
        metaDataService.createRootFolder(newUser);

        return newUser;
    }

}
