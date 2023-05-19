package cl.utem.vote.feeling.persistence.repository;

import cl.utem.vote.feeling.persistence.model.Course;
import cl.utem.vote.feeling.persistence.model.Section;
import cl.utem.vote.feeling.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sebastián Salazar Molina.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    public Course findBySectionAndUser(Section section, User user);
}
