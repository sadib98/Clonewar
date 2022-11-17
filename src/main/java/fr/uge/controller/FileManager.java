package fr.uge.controller;

import fr.uge.data.Artefact;
import org.springframework.asm.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.lang.module.FindException;
import java.lang.module.ModuleFinder;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.NoSuchElementException;
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

  public Artefact createArtefact(String jarPath) {
    boolean val = false;
    Path path = Paths.get(jarPath);
    var finder = ModuleFinder.of(path);
    var moduleReference = finder.findAll().stream().findFirst().orElseThrow();
    if(moduleReference == null){
      return null;
    }
    try (var reader = moduleReference.open()) {
      for (var filename : (Iterable<String>) reader.list()::iterator) {
        if (!filename.endsWith(".xml")) {
          continue;
        }
        var newArtefact = new Artefact();
        var inputStream = reader.open(filename).orElseThrow();
       try(var reader1 = new BufferedReader(new InputStreamReader(inputStream))){
         String line = null;
         while ((line = reader1.readLine()) != null) {
           if (line.startsWith("    <artifactId>") && line.endsWith("</artifactId>")) {
             newArtefact.setName(line.substring(16, line.length() - 13));
           }
           if (line.startsWith("    <version>") && line.endsWith("</version>")) {
             newArtefact.setVersion(line.substring(13, line.length() - 10));
           }
           if (line.startsWith("    <groupId>") && line.endsWith("</groupId>")) {
             newArtefact.setUrl(reverse(line.substring(13, line.length() - 10)+".http://www"));
           }
         }
         newArtefact.setUploadDate();
         newArtefact.setStatus(75);
         return newArtefact;
       }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    System.out.println(val);
    return  null;
  }


  public byte[] getByteCode(String jarPath) throws IOException {
    Path path = Paths.get(jarPath);
    try {
      var finder = ModuleFinder.of(path);
      var moduleReference = finder.findAll().stream().findFirst().orElseThrow();
      try (var reader = moduleReference.open()) {
        for (var filename : (Iterable<String>) reader.list()::iterator) {
          if (!filename.endsWith(".class")) {
            continue;
          }
          try (var inputStream = reader.open(filename).orElseThrow()) {
            var classReader = new ClassReader(inputStream);
            classReader.accept(new ClassVisitor(Opcodes.ASM9) {

              private static String modifier(int access) {
                if (Modifier.isPublic(access)) {
                  return "public";
                }
                if (Modifier.isPrivate(access)) {
                  return "private";
                }
                if (Modifier.isProtected(access)) {
                  return "protected";
                }
                return "";
              }

              @Override
              public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                System.err.println("class " + modifier(access) + " " + name + " " + superName + " " + (interfaces != null ? Arrays.toString(interfaces) : ""));
              }

              @Override
              public RecordComponentVisitor visitRecordComponent(String name, String descriptor, String signature) {
                System.err.println("  component " + name + " " + ClassDesc.ofDescriptor(descriptor).displayName());
                return null;
              }

              @Override
              public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
                System.err.println("  field " + modifier(access) + " " + name + " " + ClassDesc.ofDescriptor(descriptor).displayName() + " " + signature);
                return null;
              }

              @Override
              public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                System.err.println("  method " + modifier(access) + " " + name + " " + MethodTypeDesc.ofDescriptor(descriptor).displayDescriptor() + " " + signature);
                return new MethodVisitor(Opcodes.ASM9) {
                  @Override
                  public void visitInsn(int opcode) {
                    System.err.println("    opcode " + opcode);
                  }

                  @Override
                  public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                    System.err.println("    opcode " + opcode + " " + owner + "." + name + descriptor);
                  }
                };
              }
            }, 0);
          }
        }
      }
      return new byte[45];
    }catch (FindException e){
      return null;
    }catch (NoSuchElementException e){
      return null;
    }
  }
}
