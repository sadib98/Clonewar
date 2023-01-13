package fr.uge.controller;

import fr.uge.data.GroupInst;
import fr.uge.service.ArtefactDAO;
import fr.uge.service.GroupInstDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GroupInstControllerTest {

    @Autowired
    private ArtefactDAO artefactDAO;

    @Autowired
    private GroupInstDAO groupInstDAO;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    void detecteClones() {

    }


    @Test
    void saveGroupInstr() {
    }
}