package pl.brzezinski.bookt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.brzezinski.bookt.model.User;
import pl.brzezinski.bookt.model.UserRole;
import pl.brzezinski.bookt.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        convertAuthorities(user.getRoles()));
        return userDetails;
    }

    private Set<GrantedAuthority> convertAuthorities(Set<UserRole> userRoles){
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (UserRole userRole : userRoles) {
            authorities.add(new SimpleGrantedAuthority(userRole.getRole()));
        }
        return authorities;
    }
}
