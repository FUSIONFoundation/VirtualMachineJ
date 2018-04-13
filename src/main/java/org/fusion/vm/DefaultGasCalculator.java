package org.fusion.vm;

import org.fusion.vm.core.OpCode;

public class DefaultGasCalculator implements GasCalculator {

    @Override
    public int calcGas(OpCode opCode) {
        return 0;
    }

    @Override
    public int calcGas(AppSystemFunction func) {
        return 0;
    }
}
