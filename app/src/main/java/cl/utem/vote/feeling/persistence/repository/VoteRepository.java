package cl.utem.vote.feeling.persistence.repository;

import cl.utem.vote.feeling.persistence.model.Section;
import cl.utem.vote.feeling.persistence.model.Vote;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sebastián Salazar Molina.
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    public List<Vote> findBySection(Section section);
}
