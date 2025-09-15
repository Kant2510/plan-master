package com.pm.backend.service;

import com.pm.backend.model.Auth;
import com.pm.backend.repository.AuthRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Component
public class MyUserDetailService implements UserDetailsService {

    private final AuthRepo authRepo;

    public MyUserDetailService(AuthRepo authRepo) {
        this.authRepo = authRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UUID userUUID = UUID.fromString(userId);

        Optional<Auth> authRes = authRepo.findByUserProfile_Id(userUUID);

        if(authRes.isEmpty())
            throw new UsernameNotFoundException("No user found with this id" + userId);

        Auth auth = authRes.get();

        return new
                User(
                auth.getUserProfile().getId().toString(),
                auth.getPassword(),
                Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_USER")
                )
        );
    }
}
