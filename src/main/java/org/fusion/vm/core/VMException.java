package org.fusion.vm.core;

public class VMException extends Exception {


    public enum ExceptionType {
        SYSTEM_ERROR(0x00),         // Virtual Machine System error
        STACK_OVERFLOW(0x01),       // Stack Overflow
        OPCODE_ERROR(0x02),         // Unsupported OpCode
        SYSCALL_ERROR(0x03),        // Unsupported system call
        MEMORY_ERROR(0x04),         // Memory access error
        ITEM_DATA_LEN_ERROR(0x05);  // Exceed supported stack item data length

        private byte value;

        ExceptionType(int value) {
            this.value = (byte) value;
        }

        public byte getValue() {
            return value;
        }
    }

    private byte code;

    public VMException(ExceptionType type) {
        this.code = type.getValue();
    }

    public VMException(byte code) {
        this.code = code;
    }

    byte getCode() {
        return code;
    }
}
