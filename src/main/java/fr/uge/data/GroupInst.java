package fr.uge.data;


import javax.persistence.*;

@Entity
public class GroupInst {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    int idArt;

    public int getIdArt() {
        return this.idArt;
    }

    public void setIdArt(final int idArt) {
        this.idArt = idArt;
    }

    private int hash;
    private int line;
    @Column(nullable = false)
    private String file_name;

    public GroupInst(final int idArt, final int hash, final int line, final String file_name) {
        this.idArt = idArt;
        this.hash = hash;
        this.line = line;
        this.file_name = file_name;
    }

    public GroupInst(final int hash, final int line, final String file_name) {
        this.hash = hash;
        this.line = line;
        this.file_name = file_name;
    }

    public GroupInst() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getHash() {
        return this.hash;
    }

    public void setHash(final int hash) {
        this.hash = hash;
    }

    public int getLine() {
        return this.line;
    }

    public void setLine(final int line) {
        this.line = line;
    }

    public String getFile_name() {
        return this.file_name;
    }

    public void setFile_name(final String file_name) {
        this.file_name = file_name;
    }
}
