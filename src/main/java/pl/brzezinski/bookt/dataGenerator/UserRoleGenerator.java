package pl.brzezinski.bookt.dataGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.Role;
import pl.brzezinski.bookt.model.User;
import pl.brzezinski.bookt.repository.RoleRepository;
import pl.brzezinski.bookt.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
public class UserRoleGenerator {

    public static String ROLE_ADMIN = "ROLE_ADMIN";
    public static String ROLE_USER = "ROLE_USER";
    public static String ROLE_RESTAURATEUR = "ROLE_RESTAURATEUR";

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserRoleGenerator(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void createData(){
        createRoleIfNotFound(ROLE_ADMIN);
        createRoleIfNotFound(ROLE_USER);
        createRoleIfNotFound(ROLE_RESTAURATEUR);

        Role adminRole = roleRepository.findByName(ROLE_ADMIN);
        Role userRole = roleRepository.findByName(ROLE_USER);

        User admin = new User();
        admin.setName("admin");
        admin.setPassword(passwordEncoder.encode("adminPassword"));
        admin.setEmail("admin@admin.com");
        admin.setRoles(Arrays.asList(adminRole));
        userRepository.save(admin);

        System.out.println(admin.toString());

        User user = new User();
        user.setName("Pawel");
        user.setPassword(passwordEncoder.encode("userPassword"));
        user.setEmail("user@user.com");
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);

        System.out.println(user.toString());
    }


    private Role createRoleIfNotFound(String name){
        Role role = roleRepository.findByName(name);
        if (role == null){
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }

}