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
        Artefact artefact1 = new Artefact();
        File file = new File(artefact.getUrl());
        if(!artefact.getUrl().endsWith(".jar")){
            //ra.addFlashAttribute("message", "Sorry ! the file must be a .jar");
            return "redirect:upload";
        }
        try (JarFile jar = new JarFile(file)) {
            ZipEntry entry = jar.getEntry("pom.xml");
            if(entry == null){
                //ra.addFlashAttribute("message", "Sorry ! your artefact does'nt contein a pom fil");
                return "redirect:upload";
            }
            if (entry != null) {
                try (InputStream in = jar.getInputStream(entry)) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("    <name>") && line.endsWith("</name>")) {
                            artefact1.setName(line.substring(10,line.length()-7));
                        }
                        if (line.startsWith("    <version>") && line.endsWith("</version>")) {
                            artefact1.setVersion(line.substring(13,line.length()-10));
                        }
                    }
                }
            }
        }
        artefact1.setUploadDate(LocalDateTime.now());
        artefact1.setUrl("http://www.thymeleaf.org");
        artefact1.setStat(20);
        artefactDAO.save(artefact1);
        //ra.addFlashAttribute("message", "votre fichier a été ajouté avec succès");
        return "redirect:upload";
    }
}
