package fr.uge.controller;

import fr.uge.data.Artefact;
import fr.uge.service.ArtefactDAO;
import fr.uge.service.GroupInstDAO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/files")
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

    @GetMapping("/get")
    public List<Artefact> getArtefacts(){
        return artefactDAO.findAll();
    }

    @PostMapping("/upload")
    public void uploadArtefact(@RequestBody String path) throws Exception{
        Objects.requireNonNull(path);
        var filemanager = new FileManager();
        var art = filemanager.makeArtefact(path);
        if(art != null){
            artefactDAO.save(art);
            System.out.println("File uploaded !");
        }
        System.out.println("Upload Error !!");
    }

    public void deleteArtefact(int id){
        artefactDAO.deleteById(id);
        var list = groupInstDAO.findAll();
        for (var e : list){
            if(e.getIdArt() == id){
                groupInstDAO.delete(e);
            }
        }
    }


    @PostMapping("/delete")
    public void getPage(@RequestBody String id){
        deleteArtefact(Integer.parseInt(id));
        System.out.println("File deleted: " + id);
    }
}
