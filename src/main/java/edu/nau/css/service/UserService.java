package edu.nau.css.service;

import edu.nau.css.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

    User findUserByUsername(String username) throws UsernameNotFoundException;

}
