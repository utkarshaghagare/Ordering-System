package com.Restaurant.Ordering.System.Service;

import com.Restaurant.Ordering.System.Entity.Admin;
import com.Restaurant.Ordering.System.Entity.JwtRequest;
import com.Restaurant.Ordering.System.Entity.JwtResponse;
import com.Restaurant.Ordering.System.Repository.adminRepo;
import com.Restaurant.Ordering.System.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private JwtUtil jwtUtil;

//    @Autowired
//    private UserDao userDao;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private com.Restaurant.Ordering.System.Repository.adminRepo adminRepo;

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String userName = jwtRequest.getUserName();
        String userPassword = jwtRequest.getUserPassword();
        authenticate(userName, userPassword);
//
       // UserDetails userDetails = loadUserByUsername(userName);
        String newGeneratedToken = jwtUtil.generateToken(userName);
//
//        User user = userDao.findById(userName).get();
        return new JwtResponse( newGeneratedToken);
       // return new JwtResponse(user, newGeneratedToken);

    }
//
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       // User user = userDao.findById(username).get();
            Admin admin= adminRepo.findById(username).get();
        if (admin != null) {
            return new org.springframework.security.core.userdetails.User(
                    admin.getUsername(),
                    admin.getUserPassword(),
                   getAuthority(admin)
            );
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    private Set getAuthority(Admin user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + "Admin"));

//        user.getRole().forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
//        });
        return authorities;
    }

    private void authenticate(String userName, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }



}
