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
     private int status;

    /*
     public Artefact(int id, String name, String version, String url, LocalDateTime uploadDate, int stat) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.url = url;
        this.uploadDate = uploadDate;
        this.stat = stat;
    }*/

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
}
