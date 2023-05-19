package cl.utem.vote.feeling.manager;

import cl.utem.vote.feeling.api.vo.CountVO;
import cl.utem.vote.feeling.api.vo.CourseVO;
import cl.utem.vote.feeling.persistence.model.Section;
import cl.utem.vote.feeling.persistence.model.User;
import cl.utem.vote.feeling.persistence.model.Vote;
import cl.utem.vote.feeling.persistence.model.Voter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.utem.vote.feeling.persistence.repository.VoteRepository;
import cl.utem.vote.feeling.persistence.repository.VoterRepository;
import java.time.OffsetDateTime;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import cl.utem.vote.feeling.persistence.repository.SectionRepository;
import java.time.LocalDate;
import org.apache.commons.collections.CollectionUtils;

/**
 *
 * @author Sebastián Salazar Molina.
 */
@Service
public class VoteManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private transient SectionRepository sectionRepository;

    @Autowired
    private transient VoteRepository voteRepository;

    @Autowired
    private transient VoterRepository voterRepository;

    public List<CourseVO> getCourses() {
        List<CourseVO> courses = new ArrayList<>();
        List<Section> all = sectionRepository.findAll();
        if (CollectionUtils.isNotEmpty(all)) {
            for (Section section : all) {
                courses.add(new CourseVO(section));
            }
            all.clear();
        }
        return courses;
    }

    public Section getSection(final String token) {
        Section section = null;
        if (StringUtils.isNotBlank(token)) {
            section = sectionRepository.findByToken(token);
        }
        return section;
    }

    @Transactional
    public boolean vote(final User user, final Section section, final LocalDate attendance, final int choice) {
        OffsetDateTime now = OffsetDateTime.now();

        Vote vote = new Vote();
        vote.setAttendance(attendance);
        vote.setChoice(choice);
        vote.setSection(section);
        vote.setCreated(now);
        vote.setUpdated(now);
        voteRepository.save(vote);

        Voter voter = new Voter();
        voter.setAttendance(attendance);
        voter.setSection(section);
        voter.setUser(user);
        voter.setCreated(now);
        voter.setUpdated(now);
        voterRepository.save(voter);

        return true;
    }

    public List<Vote> getVotes(final Section section) {
        return voteRepository.findBySection(section);
    }

    public CountVO getAverage(final Section section) {
        List<Vote> votes = voteRepository.findBySection(section);
        double avg = 0.0;
        final int total = CollectionUtils.size(votes);
        if (CollectionUtils.isNotEmpty(votes)) {
            long sum = 0;
            for (Vote vote : votes) {
                sum += vote.getChoice();
            }
            avg = sum / total;
            votes.clear();
        }
        return new CountVO(avg, total);
    }
}
