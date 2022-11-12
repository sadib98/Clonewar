package fr.uge.data;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
public class Artefact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = true)
    private String name;
     private String version;
     private String url;
     private LocalDateTime uploadDate;
     private int stat;

    public Artefact(int id, String name, String version, String url, LocalDateTime uploadDate, int stat) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.url = url;
        this.uploadDate = uploadDate;
        this.stat = stat;
    }

    public Artefact() {
    }

    private byte[] classContains;
     //private byte[] infoContains;

    public byte[] getClassContains() {
        return classContains;
    }

    public void setClassContains(byte[] classContains) {
        this.classContains = classContains;
    }

    /**
    public byte[] getInfoContains() {
        return infoContains;
    }

    public void setInfoContains(byte[] infoContains) {
        this.infoContains = infoContains;
    }**/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVesrion(String vesrion) {
        this.version = vesrion;
    }

    public String getUrl() {
        return url;
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

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }


    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public int getId() {
        return id;
    }
}
