package fr.uge.controller;

import fr.uge.data.Artefact;
import fr.uge.data.GroupInst;
import fr.uge.service.ArtefactDAO;
import fr.uge.service.GroupInstDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.*;

@RestController
@RequestMapping("/api/analyse")
public class GroupInstController {


    @Autowired
    private ArtefactDAO artefactDAO;

    @Autowired
    private GroupInstDAO groupInstDAO;

    private ArrayList<String> fileClone = new ArrayList<>();

    public void setArtefactDAO(final ArtefactDAO artefactDAO) {
        this.artefactDAO = artefactDAO;
    }

    public void setGroupInstDAO(final GroupInstDAO groupInstDAO) {
        this.groupInstDAO = groupInstDAO;
    }

    public void setFileClone(final ArrayList<String> fileClone) {
        this.fileClone = fileClone;
    }

    public  String stringSplit(String arg) {
        String[] arrOfStr = arg.split("/");
        return arrOfStr[arrOfStr.length-1];
    }

    public boolean detecteClones(int idArtclone, int hashInst, int idInst, int idArt, List<GroupInst> listInst){
        boolean nbClone = false;
        for (var inst : listInst){
            if(idInst != inst.getId() && hashInst == inst.getHash() && inst.getIdArt() != idArt && inst.getIdArt() == idArtclone){
                var art = artefactDAO.findById(idArtclone);
                if(art.isPresent()) {
                    fileClone.add(stringSplit(art.get().getName()));
                    return true;
                }
            }
        }
        return nbClone;
    }


    public String uploadGroupInstr(int id) throws Exception{
        var artefact = new Artefact();
        var artefactList = artefactDAO.findAll();
        for(var art : artefactList){
            if(art.getId() == id){
                artefact = art;
                var existe = groupInstDAO.findAll();
                for (var e : existe) {
                    if (e.getIdArt() == id) {
                        return "";
                    }
                }
            }
        }
        var checkFile = new FileManager();
        var b = checkFile.getByteCode(artefact.getPath(), id);
        if(b == null){
            return "";
        }
        groupInstDAO.saveAll(b.groupInstList);
        return "";
    }

    public double clonePersent(int id, int idArtClone, List<GroupInst> listInst){
        double persent = 0;
        for(var e : listInst){
            if(e.getIdArt() == id){
                persent = detecteClones(idArtClone, e.getHash(), e.getId(), e.getIdArt(), listInst)? persent+1:persent;
            }
        }
        return ((persent)*100)/listInst.stream().filter(e->e.getIdArt() == id).toList().size();
    }

    @PostMapping("/result")
    public Map<String, String> analyse(@RequestBody String id) throws Exception {
        var analyseID = Integer.parseInt(id);
        System.out.println("Analysing : " + analyseID);

        DecimalFormat f = new DecimalFormat();
        f.setMaximumFractionDigits(2);
        uploadGroupInstr(analyseID);
        List<GroupInst> list = groupInstDAO.findAll();
        var ListeArt = artefactDAO.findAll();
        HashMap<String, String> map = new HashMap<>();
        for(var a : ListeArt){
            if(a.getId() != analyseID){
                var persent = clonePersent(analyseID, a.getId(), list);
                map.put(a.getName()+", version "+a.getVersion()+",  Id  =  "+a.getId(),  f.format(persent));
            }
        }
        return map;
    }
}
