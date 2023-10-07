package cl.utem.feeling.persistence.repository;

import cl.utem.feeling.persistence.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sebastián Salazar Molina.
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    public Subject findByCode(String code);
}
