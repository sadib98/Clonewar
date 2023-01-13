package fr.uge.controller;

import fr.uge.data.GroupInst;
import org.springframework.asm.*;

import java.io.FileWriter;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

public class MyClassVisitor extends ClassVisitor{

    protected ArrayList<GroupInst> groupInstList = new ArrayList<>();
    protected String fileName;
    private FileWriter writer = null;

    protected int idArt;

    public MyClassVisitor(int api) {
        super(Opcodes.ASM9);
    }

    public MyClassVisitor() {
        super(Opcodes.ASM9);
    }

    private StringBuilder builder = new StringBuilder();
    private int visitedInst = 0;
    private int instrNumber = 0;
    private int fenetre = 2;
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

    public int getVisitedInst() {
        return this.visitedInst;
    }

    public void setVisitedInst(final int visitedInst) {
        this.visitedInst = visitedInst;
    }

    public int getInstrNumber() {
        return this.instrNumber;
    }

    public void setInstrNumber(final int instrNumber) {
        this.instrNumber = instrNumber;
    }

    public int hash(String s) {
        int hash = 0;
        int n = s.length();
        for(var i = 0; i < n; i++) {
            hash += s.charAt(i)*i;
        }
        return hash;
    }

    public boolean stockeInstr(String text, int visitedInst, int instrNumber){
        builder.append(text).append('\n');
        if(visitedInst == fenetre){
            System.out.println(builder.toString() +" : has = " +hash(builder.toString())+"  bigins at : "+ (instrNumber-1)+"\n");
            groupInstList.add(new GroupInst(idArt, hash(builder.toString()), (instrNumber-1),fileName));
            builder.delete(0,builder.length());
            return true;
        }
        return false;
    }
    
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        String text = "class " + modifier(access) + " " + name + " " + superName + " " + (interfaces != null ? Arrays.toString(interfaces) : "");
        visitedInst++;
        instrNumber++;
        var stoked = stockeInstr(text, visitedInst, instrNumber);
        visitedInst = stoked? 0 : visitedInst;
    }

    @Override
    public RecordComponentVisitor visitRecordComponent(String name, String descriptor, String signature) {
        String text = "  component " + name + " " + ClassDesc.ofDescriptor(descriptor).displayName() ;
        visitedInst++;
        instrNumber++;
        var stoked = stockeInstr(text, visitedInst, instrNumber);
        visitedInst = stoked? 0 : visitedInst;
        return null;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        String text = "  " + modifier(access) +  " " + ClassDesc.ofDescriptor(descriptor).displayName() + " " + signature;
        visitedInst++;
        instrNumber++;
        var stoked = stockeInstr(text, visitedInst, instrNumber);
        visitedInst = stoked? 0 : visitedInst;
        return null;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        return new MethodVisitor(Opcodes.ASM9) {
            @Override
            public void visitInsn(int opcode) {
                String tex1 = "  " + modifier(access) +  " " + MethodTypeDesc.ofDescriptor(descriptor).displayDescriptor() + " " + signature;
                visitedInst++;
                instrNumber++;
                var stoked = stockeInstr(tex1, visitedInst, instrNumber);
                visitedInst = stoked? 0 : visitedInst;
                String text = "    " + opcode;
                visitedInst++;
                instrNumber++;
                stoked = stockeInstr(text, visitedInst, instrNumber);
                visitedInst = stoked? 0 : visitedInst;
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                String text = "    " + opcode + " " + name + descriptor;
                visitedInst++;
                instrNumber++;
                var stoked = stockeInstr(text, visitedInst, instrNumber);
                visitedInst = stoked? 0 : visitedInst;
            }
        };
    }

    public void visiteEnd(){
        instrNumber = 0;
    }
}
