package org.fusion.vm;

import org.fusion.vm.core.*;

public class AppVirtualMachine extends VirtualMachine {

    private int gas;
    private int usedGas = 0;
    private SystemContext systemContext;
    private GasCalculator calculator = new DefaultGasCalculator(); // Will be confirmed after the economic model release

    public int getGas() {
        return gas;
    }

    public int getUsedGas() {
        return usedGas;
    }


    public GasCalculator getCalculator() {
        return calculator;
    }

    public AppVirtualMachine(SystemContext systemContext, Stack stack) {
        super(systemContext.getContext(), new AppSystemService(systemContext), stack);
        this.systemContext = systemContext;
        gas = systemContext.getGas();
    }

    @Override
    public State execute() {
        State state = super.execute();
        if (!state.hasFlag(State.REVERT) && systemContext.getLevel() == 0) {
            systemContext.flush();
        }
        return state;
    }

    @Override
    public void executeOp(OpCode opCode) throws VMException {
        useGas(opCode);
        super.executeOp(opCode);
    }

    private void useGas(OpCode opCode) throws AppVMException {
        int needGas = calculator.calcGas(opCode);
        useGas(needGas);
    }

    public void useGas(int needGas) throws AppVMException {
        if (usedGas + needGas > gas) {
            throw new AppVMException(AppVMException.ExceptionType.OUT_OF_GAS);
        }
        usedGas += needGas;
    }


}
