package fr.uge.service;

import fr.uge.data.Artefact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArtefactDAO extends JpaRepository<Artefact, Integer> {
    //@Query("SELECT d.id, d.name, d.version, d.url, d.uploadDate, d.stat  FROM Artefact d ORDER BY d.uploadDate DESC ")
    //List<Artefact> findAll();
}
