package cl.utem.feeling.persistence.repository;

import cl.utem.feeling.persistence.model.Section;
import cl.utem.feeling.persistence.model.Subject;
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
