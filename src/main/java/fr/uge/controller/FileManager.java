package fr.uge.controller;

import fr.uge.data.Artefact;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class FileManager {
  private final Artefact fileToCheck;
  private File receivedFile;
  private JarFile jarFile;
  private ZipEntry pomEntry;

  public FileManager(Artefact newFile){
    if(newFile == null){
      throw new IllegalArgumentException("file == null");
    }
    this.fileToCheck = newFile;
  }

  public boolean isJarFormat(){
    if(!fileToCheck.getUrl().endsWith(".jar")){
      return false;
    }
    this.receivedFile = new File(fileToCheck.getUrl());
    return true;
  }

  public boolean containsPom(){
    try {
      this.jarFile = new JarFile(receivedFile);
      this.pomEntry = jarFile.getEntry("pom.xml");
      if(pomEntry == null){
        return false;
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return true;
  }

  public Artefact createArtefact(){
    try{
      var newArtefact = new Artefact();
      var input = jarFile.getInputStream(pomEntry);
      var reader = new BufferedReader(new InputStreamReader(input));
      String line = null;
      while((line = reader.readLine()) != null){
        if (line.startsWith("    <name>") && line.endsWith("</name>")) {
          newArtefact.setName(line.substring(10,line.length()-7));
        }
        if (line.startsWith("    <version>") && line.endsWith("</version>")) {
          newArtefact.setVersion(line.substring(13,line.length()-10));
        }
      }
      newArtefact.setUploadDate();
      newArtefact.setUrl("http://www.thymeleaf.org");
      newArtefact.setStatus(20);
      return newArtefact;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
