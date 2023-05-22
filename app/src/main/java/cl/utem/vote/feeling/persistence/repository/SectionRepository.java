package cl.utem.vote.feeling.persistence.repository;

import cl.utem.vote.feeling.persistence.model.Section;
import cl.utem.vote.feeling.persistence.model.Subject;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sebastián Salazar Molina.
 */
@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    public Section findByToken(String token);

    public List<Section> findBySubject(Subject subject);
}
