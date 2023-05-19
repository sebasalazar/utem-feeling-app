package cl.utem.vote.feeling.persistence.repository;

import cl.utem.vote.feeling.persistence.model.ApiAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiAuthRepository extends JpaRepository<ApiAuth, Long> {

    public ApiAuth findByTokenAndApiKey(String token, String apiKey);
}
