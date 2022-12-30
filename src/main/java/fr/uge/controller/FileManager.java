package fr.uge.controller;

import fr.uge.data.Artefact;
import org.springframework.asm.ClassReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.module.FindException;
import java.lang.module.ModuleFinder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public class FileManager {
  private final Artefact fileToCheck;

  public FileManager(Artefact newFile){
    if(newFile == null){
      throw new IllegalArgumentException("file == null");
    }
    this.fileToCheck = newFile;
  }


  // This function correctes a string represented an artefact  groupeId in an order readable by humam
  // in the goal of setting artafact's url
  public String reverse(String s) {
    var st = s.replace('.', ' ').split(" ");
    var i = st.length-1;
    var str = new StringBuilder();
    while(i > 0) {
      str.append(st[i]).append(".");
      i--;
    }
    str.append(st[i]);
    return str.toString();
  }


  private Artefact createArtefact(BufferedReader reader, String path) throws IOException {
    var newArtefact = new Artefact();
    var take = true;
    Pattern pattern1 = Pattern.compile("\\s*<artifactId>[A-Za-z0-9._-]+\\</artifactId>");
    Pattern pattern2 = Pattern.compile("\\s*<groupId>[A-Za-z0-9._-]+\\</groupId>");
    Pattern pattern3 = Pattern.compile("\\s*<version>[A-Za-z0-9._-]+\\</version>");
    String line = null;
    while ((line = reader.readLine()) != null) {
      if (pattern1.matcher(line).matches() && take == true) {
        newArtefact.setName(line.split(">")[1].split("<")[0]);
      }
      if (pattern2.matcher(line).matches()  && take == true) {
        newArtefact.setUrl(reverse(line.split(">")[1].split("<")[0]+".http://www"));
      }
      if (pattern3.matcher(line).matches()  && take == true) {
        newArtefact.setVersion(line.split(">")[1].split("<")[0]);
      }
      if(line.endsWith("<dependencies>") || line.endsWith("<plugin>")){
        take = false;
      }
    }
    newArtefact.setPath(path);
    newArtefact.setUploadDate();
    newArtefact.setStatus(75);
    return newArtefact;
  }

  public Artefact makeArtefact(String path) throws IOException {
    File dir = new File(path);
    var files = dir.listFiles();
    for(var f:files){
      if(! f.getName().endsWith(".pom") && ! f.getName().endsWith("pom.xml")){
        continue;
      }
      try(var reader = Files.newBufferedReader(Path.of(f.toURI()))){
        return createArtefact(reader, path);
      }
    }
    return null;
  }




  public MyClassVisitor getByteCode(String jarPath, int idArt) throws IOException {
    Path path = Paths.get(jarPath);
    var myclassVisitor = new MyClassVisitor();
    myclassVisitor.idArt = idArt;
    try {
      var finder = ModuleFinder.of(path);
      var moduleReference = finder.findAll().stream().findFirst().orElseThrow();
      if(moduleReference == null){
        return null;
      }
      try (var reader = moduleReference.open()) {
        for (var filename : (Iterable<String>) reader.list()::iterator) {
          if (!filename.endsWith(".class")) {
            continue;
          }
          try (var inputStream = reader.open(filename).orElseThrow()) {
            var classReader = new ClassReader(inputStream);
            myclassVisitor.fileName = filename;
            classReader.accept(myclassVisitor, 0);
            myclassVisitor.setInstrNumber(0);
            myclassVisitor.setVisitedInst(0);
          }
        }
      }
    }catch (FindException e){
      System.out.println("no modul finded  1");
      return null;
    }catch (NoSuchElementException e){
      System.out.println("no modul finded 2");
      return null;
    }
    return  myclassVisitor;
  }
}
