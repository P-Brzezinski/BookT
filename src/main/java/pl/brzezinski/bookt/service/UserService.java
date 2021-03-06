package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.users.User;
import pl.brzezinski.bookt.model.users.Role;
import pl.brzezinski.bookt.repository.UserRepository;
import pl.brzezinski.bookt.repository.RoleRepository;

import java.util.List;

@Service
public class UserService implements GenericService<Long, User>{

    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_RESTAURATEUR = "ROLE_RESTAURATEUR";

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
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void addWithUserRole(User user){
        Role userRole = roleRepository.findByName(ROLE_USER);
        user.getRoles().add(userRole);
        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        userRepository.save(user);
    }

    public void addWithRestaurateurRole(User user) {
        Role restaurateurRole = roleRepository.findByName(ROLE_RESTAURATEUR);
        user.getRoles().add(restaurateurRole);
        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        userRepository.save(user);
    }
}
