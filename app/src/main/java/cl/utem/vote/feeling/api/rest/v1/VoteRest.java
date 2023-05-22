package cl.utem.vote.feeling.api.rest.v1;

import cl.utem.vote.feeling.api.vo.CountVO;
import cl.utem.vote.feeling.api.vo.CourseVO;
import cl.utem.vote.feeling.api.vo.ResponseVO;
import cl.utem.vote.feeling.api.vo.VoteVO;
import cl.utem.vote.feeling.exception.AuthException;
import cl.utem.vote.feeling.exception.UtemException;
import cl.utem.vote.feeling.manager.ApiManager;
import cl.utem.vote.feeling.manager.VoteManager;
import cl.utem.vote.feeling.persistence.model.Section;
import cl.utem.vote.feeling.persistence.model.User;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = {"/v1/voter"},
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
public class VoteRest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private transient ApiManager apiManager;

    @Autowired
    private transient VoteManager voteManager;

    @GetMapping(value = "/courses",
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<CourseVO>> courses(@RequestHeader("Authorization") String authorization) {
        final User user = apiManager.authUser(authorization);
        if (user == null) {
            throw new AuthException();
        }

        List<CourseVO> courses = voteManager.getCourses();
        if (CollectionUtils.isEmpty(courses)) {
            throw new UtemException(404, "No se han encontrado cursos para mostrar");
        }

        return ResponseEntity.ok(courses);
    }

    @PostMapping(value = "/vote",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseVO> requestToken(@RequestHeader("Authorization") String authorization,
            @RequestBody VoteVO body) {
        final User user = apiManager.authUser(authorization);
        if (user == null) {
            throw new AuthException();
        }

        final String sectionToken = StringUtils.trimToEmpty(body.getSectionToken());
        final Section section = voteManager.getSection(sectionToken);
        if (section == null) {
            throw new UtemException(String.format("NO se ha encontrado ningún curso con el identificador %s", sectionToken));
        }

        final LocalDate attendance = body.getAttendance();
        if (attendance == null) {
            throw new UtemException("La fecha es inválida");
        }

        final Integer choice = body.getChoice();
        if (choice == null || choice < 0 || choice > 10) {
            throw new UtemException("La selección es inválida");
        }

        final boolean vote = voteManager.vote(user, section, attendance, choice);
        return ResponseEntity.ok(new ResponseVO(vote, "Votación registrada"));
    }

    @GetMapping(value = "/{sectionToken}/results", consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CountVO> results(@RequestHeader("Authorization") String authorization,
            @PathVariable("sectionToken") String sectionToken) {
        final User user = apiManager.authUser(authorization);
        if (user == null) {
            throw new AuthException();
        }

        final Section section = voteManager.getSection(sectionToken);
        if (section == null) {
            throw new UtemException(String.format("NO se ha encontrado ningún curso con el identificador %s", sectionToken));
        }

        final CountVO avg = voteManager.getAverage(section);
        return ResponseEntity.ok(avg);
    }

    @GetMapping(value = "/results", consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<CourseVO, CountVO>> allResults(@RequestHeader("Authorization") String authorization) {
        final User user = apiManager.authUser(authorization);
        if (user == null) {
            throw new AuthException();
        }

        List<CourseVO> courses = voteManager.getCourses();
        if (CollectionUtils.isEmpty(courses)) {
            throw new UtemException(404, "No hay cursos disponibles");
        }

        Map<CourseVO, CountVO> map = new HashMap<>();
        for (CourseVO course : courses) {
            final Section section = voteManager.getSection(course.getToken());
            final CountVO count = voteManager.getAverage(section);
            map.put(course, count);
        }

        return ResponseEntity.ok(map);
    }
}
