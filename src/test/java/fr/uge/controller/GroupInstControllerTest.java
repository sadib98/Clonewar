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
        var g1 = new GroupInst();
        g1.setFile_name("classe1");
        g1.setHash(100);
        g1.setLine(10);
        g1.setIdArt(1);
        var g2 = new GroupInst();
        g2.setFile_name("classe2");
        g2.setHash(100);
        g2.setLine(10);
        g2.setIdArt(2);

        groupInstDAO.saveAll(List.of(g1,g2));

        var c = new GroupInstController();
        c.setGroupInstDAO(groupInstDAO);
        c.setArtefactDAO(artefactDAO);
        assertTrue(c.detecteClones(2, 100, 1, 1, List.of(g1,g2)));
    }


    @Test
    void saveGroupInstr() {
        var g1 = new GroupInst();
        g1.setFile_name("classe1");
        g1.setHash(100);
        g1.setLine(10);
        g1.setIdArt(1);

        var g2 = new GroupInst();
        g2.setFile_name("classe2");
        g2.setHash(100);
        g2.setLine(10);
        g2.setIdArt(2);
        groupInstDAO.saveAll(List.of(g1,g2));
        GroupInst existGroupInst = testEntityManager.find(GroupInst.class, g1.getId());
        assertTrue(existGroupInst.getFile_name().equals("classe1"));
        existGroupInst = testEntityManager.find(GroupInst.class, g2.getId());
        assertTrue(existGroupInst.getFile_name().equals("classe2"));
    }
}