package org.fusion.vm.core;

public class State {

    public static final int NONE = 0x00;
    public static final int HALT = 0x01;
    public static final int REVERT = 0x02;

    private int state = NONE;

    public int getValue() {
        return state;
    }

    public void setFlag(int flag) {
        state = state | flag;
    }

    public boolean hasFlag(int flag) {
        return (state & flag) != 0;
    }


}
