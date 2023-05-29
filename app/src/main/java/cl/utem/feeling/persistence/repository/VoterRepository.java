package cl.utem.feeling.persistence.repository;

import cl.utem.feeling.persistence.model.Section;
import cl.utem.feeling.persistence.model.User;
import cl.utem.feeling.persistence.model.Voter;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sebastián Salazar Molina.
 */
@Repository
public interface VoterRepository extends JpaRepository<Voter, Long> {

    public Voter findByUserAndSectionAndAttendance(User user, Section section, LocalDate attendance);
}
