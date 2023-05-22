package cl.utem.vote.feeling.persistence.repository;

import cl.utem.vote.feeling.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sebastián Salazar Molina.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmailIgnoreCase(String email);
}
