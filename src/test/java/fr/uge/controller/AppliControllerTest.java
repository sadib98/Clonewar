package fr.uge.controller;

import fr.uge.data.Artefact;
import fr.uge.service.ArtefactDAO;
import fr.uge.service.GroupInstDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AppliControllerTest {
    @Autowired
    private ArtefactDAO artefactDAO;

    @Autowired
    private GroupInstDAO groupInstDAO;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void viewHomePage() {
    }

    @Test
    void uploadArtefact() {

    }

    @Test
    void deleteArtefact(){
    }

    @Test
    void getPage() {
    }
}