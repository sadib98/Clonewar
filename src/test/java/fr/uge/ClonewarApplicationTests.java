package fr.uge;

import fr.uge.data.Artefact;
import fr.uge.service.ArtefactDAO;
import org.hibernate.type.LocalDateTimeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClonewarApplicationTests {

    @Autowired
    private ArtefactDAO artefactDAO;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @Rollback(value = false)
    void testInsertArtefact() throws IOException {
        File file = new File("/home/bruce/Bureau/M1/java/out/artifacts/demo.jar");
        Artefact artefact = new Artefact();
        artefact.setName(file.getName());
        artefact.setUploadDate();
        artefact.setUrl("http://www.thymeleaf.org");
        artefact.setVersion("0.0.1");
        artefact.setStatus(0);
        byte[] bytes = Files.readAllBytes(file.toPath());
        artefact.setClassContains(bytes);

        Artefact savedArtefact = artefactDAO.save(artefact);
        Artefact existAtefact = testEntityManager.find(Artefact.class, savedArtefact.getId());
        assertTrue(existAtefact.getName().equals(file.getName()));
    }

}
