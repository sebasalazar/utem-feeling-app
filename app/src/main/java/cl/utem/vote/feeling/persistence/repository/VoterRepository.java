package cl.utem.vote.feeling.persistence.repository;

import cl.utem.vote.feeling.persistence.model.Section;
import cl.utem.vote.feeling.persistence.model.User;
import cl.utem.vote.feeling.persistence.model.Voter;
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
