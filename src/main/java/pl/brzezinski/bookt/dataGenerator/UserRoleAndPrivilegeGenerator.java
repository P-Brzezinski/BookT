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
public class UserRoleAndPrivilegeGenerator {

    public static String ADMIN_ROLE = "ROLE_ADMIN";
    public static String USER_ROLE = "ROLE_USER";
    public static String RESTAURATEUR_ROLE = "ROLE_RESTAURATEUR";

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserRoleAndPrivilegeGenerator(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void createData(){
        createRoleIfNotFound(ADMIN_ROLE);
        createRoleIfNotFound(USER_ROLE);
        createRoleIfNotFound(RESTAURATEUR_ROLE);

        Role adminRole = roleRepository.findByName(ADMIN_ROLE);
        User admin = new User();
        admin.setName("admin");
        admin.setPassword(passwordEncoder.encode("adminPassword"));
        admin.setEmail("admin@admin.com");
        admin.setRoles(Arrays.asList(adminRole));
        userRepository.save(admin);

        Role userRole = roleRepository.findByName(USER_ROLE);
        User user = new User();
        user.setName("Pawel");
        user.setPassword(passwordEncoder.encode("userPassword"));
        user.setEmail("user@user.com");
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);
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
