package pl.brzezinski.bookt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.brzezinski.bookt.model.Table;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {
}
