package cl.utem.vote.feeling.persistence.repository;

import cl.utem.vote.feeling.persistence.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {

    public Credential findByState(String token);
}
