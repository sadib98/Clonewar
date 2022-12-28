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
        Artefact artefact = new Artefact();
        artefact.setName("thymleaf");
        artefact.setUploadDate();
        artefact.setUrl("http://www.thymeleaf.org");
        artefact.setVersion("0.0.1");
        artefact.setStatus(0);

        Artefact savedArtefact = artefactDAO.save(artefact);
        Artefact existAtefact = testEntityManager.find(Artefact.class, savedArtefact.getId());
        assertTrue(existAtefact.getName().equals("thymleaf"));

    }

    @Test
    void deleteArtefact(){
        Artefact artefact = new Artefact();
        artefact.setName("thymleaf");
        artefact.setUploadDate();
        artefact.setUrl("http://www.thymeleaf.org");
        artefact.setVersion("0.0.1");
        artefact.setStatus(0);

        Artefact savedArtefact = artefactDAO.save(artefact);
        Artefact existAtefact = testEntityManager.find(Artefact.class, savedArtefact.getId());
        assertTrue(existAtefact.getName().equals("thymleaf"));
        var c = new AppliController();
        c.setArtefactDAO(artefactDAO);
        c.setGroupInstDAO(groupInstDAO);
        c.deleteArtefact(artefact.getId());
        existAtefact = testEntityManager.find(Artefact.class, savedArtefact.getId());
        assertNull(existAtefact);

    }

    @Test
    void getPage() {
    }
}