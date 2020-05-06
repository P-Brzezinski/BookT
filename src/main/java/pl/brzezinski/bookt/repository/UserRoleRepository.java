package pl.brzezinski.bookt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.brzezinski.bookt.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findByRole(String role);
}
