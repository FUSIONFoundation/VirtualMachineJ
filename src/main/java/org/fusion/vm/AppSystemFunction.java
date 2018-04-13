package org.fusion.vm;


public enum AppSystemFunction {

    HTTP(0xb1),         // Via URL to retrieve JSON, return JSON resource id
    JSON_GET(0xb2),     // get a long value from JSON resource id with JSON key
    INVOKE(0xb3),       // Call other smart contract
    TRANSFER(0xb4),     // Transfer FSN
    BALANCE(0xb5),      // Balance of address
    SENDER(0xb6),       // Get the address of FSN sender
    RECEIVER(0xb7),     // Get the address of FSN receiver
    SEND_VALUE(0xb8),   // Get the sent amount
    BLOCK_HASH(0xb9),   // Get the block hash
    BLOCK_NUMBER(0xba), // Get the block number
    DIFF(0xbb),         // Get the difficulty of the block
    TIMESTAMP(0xbc),    // Get current UNIX time stamp
    SLOAD(0xbd),        // Load a stack item from storage
    SSTORE(0xbe);       // Save a stack item to storage


    private byte value;

    public byte getValue() {
        return value;
    }

    AppSystemFunction(int value) {
        this.value = (byte) value;
    }

    private static final AppSystemFunction[] FUNC_LIST = new AppSystemFunction[256];

    static {
        for (AppSystemFunction func : AppSystemFunction.values()) {
            FUNC_LIST[func.value & 0xFF] = func;
        }
    }

    public static AppSystemFunction fromByte(byte funcValue) {
        return FUNC_LIST[funcValue & 0xff];
    }
}
