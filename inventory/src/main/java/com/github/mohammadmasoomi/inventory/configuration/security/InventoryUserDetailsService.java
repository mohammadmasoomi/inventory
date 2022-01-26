package com.github.mohammadmasoomi.inventory.configuration.security;

import com.github.mohammadmasoomi.inventory.core.entity.security.User;
import com.github.mohammadmasoomi.inventory.core.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public InventoryUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<String> userPermissionCodes = userRepository.findUserPermissionCodes(user.getId());
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String p : userPermissionCodes) {
            authorities.add(new SimpleGrantedAuthority(p));
        }
        //if credentialIsNonExpired and accountNonLocked is set to false, client get security error message
        //we can set a business for these two properties
        return new User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true, authorities);
    }

}