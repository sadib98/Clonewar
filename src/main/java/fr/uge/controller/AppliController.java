package fr.uge.controller;

import fr.uge.data.Artefact;
import fr.uge.service.ArtefactDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AppliController {
    @Autowired
    private ArtefactDAO artefactDAO;

    @GetMapping("/upload")
    public String viewHomePage(Model model){
        List<Artefact> listArtfacts = artefactDAO.findAll();
        model.addAttribute("listArtfacts", listArtfacts);
        model.addAttribute("artefact", new Artefact());
        return "home";
    }

    @PostMapping("/upload")
    public String getMessage(@ModelAttribute Artefact  artefact, RedirectAttributes ra) throws Exception{
        var checkFile = new FileManager(artefact);
        var b = checkFile.getByteCode(artefact.getUrl());
        if(b == null){
            return "redirect:upload";
        }
        var art = checkFile.createArtefact(artefact.getUrl());
        if(art != null){
            artefactDAO.save(art);
        }
        return "redirect:upload";
    }
}
