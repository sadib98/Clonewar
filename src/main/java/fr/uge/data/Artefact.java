package fr.uge.data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Artefact {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column()
    private String name;
     private String version;
     private String url;
     private LocalDateTime uploadDate;
     private int status;

     private String path;

    public String getPath() {
        return this.path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

   // @OneToMany(targetEntity = GroupInst.class)
     //private List<GroupInst> groupInstList;

    //public List<GroupInst> getGroupInstList() {
      //  return this.groupInstList;
   // }

   // public void setGroupInstList(final List<GroupInst> groupInstList) {
       // this.groupInstList = groupInstList;


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUploadDate() {
        this.uploadDate = LocalDateTime.now();
    }
    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Artefact() {
    }

    public Artefact(final int id) {
        this.id = id;
    }
}
