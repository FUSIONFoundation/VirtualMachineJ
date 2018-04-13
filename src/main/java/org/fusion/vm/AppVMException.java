package org.fusion.vm;

import org.fusion.vm.core.VMException;

public class AppVMException extends VMException {

    public enum ExceptionType {
        OUT_OF_GAS(0x80),       // Out of gas
        STORAGE_ERROR(0x81);    // Storage access error

        private byte value;

        ExceptionType(int value) {
            this.value = (byte) value;
        }

        public byte getValue() {
            return value;
        }
    }


    public AppVMException(ExceptionType type) {
        super(type.getValue());
    }
}
