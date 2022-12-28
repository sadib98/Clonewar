package fr.uge.controller;

import fr.uge.data.Artefact;
import fr.uge.service.ArtefactDAO;
import fr.uge.service.GroupInstDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@EnableTransactionManagement
@Controller
public class AppliController {

    @Autowired
    private ArtefactDAO artefactDAO;

    @Autowired
    private GroupInstDAO groupInstDAO;

    public void setArtefactDAO(final ArtefactDAO artefactDAO) {
        this.artefactDAO = artefactDAO;
    }

    public void setGroupInstDAO(final GroupInstDAO groupInstDAO) {
        this.groupInstDAO = groupInstDAO;
    }

    @GetMapping("/upload")
    public String viewHomePage(Model model){
        List<Artefact> listArtfacts = artefactDAO.findAll();
        model.addAttribute("listArtfacts", listArtfacts);
        model.addAttribute("artefact", new Artefact());
        return "home";
    }

    @PostMapping("/upload")
    public String uploadArtefact(@ModelAttribute Artefact  artefact, RedirectAttributes ra) throws Exception{
        var checkFile = new FileManager(artefact);
        var art = checkFile.makeArtefact(artefact.getUrl());
        if(art != null){
            artefactDAO.save(art);
            //groupInstDAO.saveAll(b.groupInstList);
        }
        return "redirect:upload";
    }

    @PostMapping()
    public void deleteArtefact(@Param("id") int id){
        artefactDAO.deleteById(id);
        var list = groupInstDAO.findAll();
        for (var e : list){
            if(e.getIdArt() == id){
                groupInstDAO.delete(e);
            }
        }
    }

    @GetMapping("/suppr")
    public String getPage(@Param("id") int id){
        deleteArtefact(id);
        return "redirect:upload";
    }
}
