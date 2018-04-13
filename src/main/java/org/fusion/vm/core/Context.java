package org.fusion.vm.core;

import java.util.Stack;

public class Context {

    private Stack<Integer> returnList = new Stack<>();

    private int programCounter = 0;

    private byte[] codeArray;


    public Context(byte[] codeArray) {
        this.codeArray = codeArray;
    }


    byte getOpCodeVal() {
        return codeArray[programCounter++];
    }

    void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }

    void call(int programCounter) {
        returnList.push(programCounter);
        this.programCounter = programCounter;
    }

    boolean ret() {
        if (returnList.isEmpty()) {
            return false;
        }
        this.programCounter = returnList.pop();
        return true;
    }

    private byte[] getData(int offset, int length) {
        byte[] data = new byte[length];
        System.arraycopy(codeArray, offset, data, 0, length);
        return data;
    }

    byte[] getData(int length) {
        return getData(programCounter, length);
    }

    void addProgramCounter(int offset) {
        programCounter += offset;
    }
}
