package org.fusion.vm.core;

public interface SystemService {
    State systemCall(byte funcValue, Stack stack, VirtualMachine vm) throws VMException;
}
