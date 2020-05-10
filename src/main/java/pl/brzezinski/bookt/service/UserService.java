package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.User;
import pl.brzezinski.bookt.model.Role;
import pl.brzezinski.bookt.repository.UserRepository;
import pl.brzezinski.bookt.repository.RoleRepository;

import java.util.List;

@Service
public class UserService implements GenericService<Long, User>{

    private static final String DEFAULT_ROLE = "ROLE_USER";

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User get(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    public void remove(User user){
        userRepository.delete(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void addWithDefaultRole(User user){
        Role defaultRole = roleRepository.findByName(DEFAULT_ROLE);
        user.getRoles().add(defaultRole);
        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        userRepository.save(user);
    }
}
