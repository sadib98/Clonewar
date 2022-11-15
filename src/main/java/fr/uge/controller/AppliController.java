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

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

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

   /**
    @Transactional
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("artefact")MultipartFile multipartFile, RedirectAttributes ra) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Artefact artefact = new Artefact();
        artefact.setName(fileName);
        var finder = ModuleFinder.of(Path.of(new File(fileName).getParent()));
        var moduleReference = finder.findAll().stream().findFirst().orElseThrow();
        try(var reader = moduleReference.open()) {
            for (var filename : (Iterable<String>) reader.list()::iterator) {
                if (!filename.equals("pom.xml")) {
                    continue;
                }
                try (var in = reader.open(filename).orElseThrow()) {
                    BufferedReader reader1 = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    while ((line = reader1.readLine()) != null) {
                        if (line.startsWith("    <name>") && line.endsWith("</name>")) {
                            artefact.setName(line);
                        }
                        if (line.startsWith("    <version>") && line.endsWith("</version>")) {
                            artefact.setVersion(line);
                        }
                    }
                }
            }
        }
        //artefact.setClassContains(multipartFile.getBytes());
        artefact.setVesrion("0.0.1");
        artefact.setUploadDate(LocalDateTime.now());
        artefact.setUrl("http://www.thymeleaf.org");
        artefact.setStat(0);
        artefactDAO.save(artefact);
        ra.addFlashAttribute("message", "votre fichier a été ajouté avec succès");
        return "redirect:/";
    }
    **/


    @PostMapping("/upload")
    public String getMessage(@ModelAttribute Artefact  artefact, RedirectAttributes ra) throws Exception{
        var checkFile = new FileManager(artefact);
        if(checkFile.isJarFormat() && checkFile.containsPom()){
            var newArtefact = checkFile.createArtefact();
            artefactDAO.save(newArtefact);
        }
        return "redirect:upload";
    }
}
