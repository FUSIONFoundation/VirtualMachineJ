package org.fusion.vm;

import org.fusion.vm.core.OpCode;

public interface GasCalculator {
    int calcGas(OpCode opCode);

    int calcGas(AppSystemFunction func);
}
