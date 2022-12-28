package fr.uge.service;

import fr.uge.data.Artefact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public interface ArtefactDAO extends JpaRepository<Artefact, Integer> {

}
